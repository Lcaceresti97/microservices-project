package com.sti.carservice.service.implementation;

import com.sti.carservice.dto.CarDto;
import com.sti.carservice.exception.CarNotFoundException;
import com.sti.carservice.model.Car;
import com.sti.carservice.model.mapper.CarMapper;
import com.sti.carservice.model.status.ModelStatus;
import com.sti.carservice.repository.CarRepository;
import com.sti.carservice.service.CarService;
import com.sti.carservice.utils.SortingPagingUtils;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Car entity.
 * @author Laurent Caceres
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    private final SortingPagingUtils sortingPagingUtils;

    @Override
    public CarDto saveCar(final CarDto carDto) {
        Car car = Car.buildFromDto(this.carMapper.dtoToCar(carDto));
        this.carRepository.save(car);
        return carMapper.carToDto(car);
    }

    @Override
    public CarDto findCarById(String carId) throws CarNotFoundException {
        Car car = carRepository.findById(carId).orElseThrow(() -> CarNotFoundException.buildCarNotFoundExceptionForId(carId));
        return carMapper.carToDto(isActiveCar(car,"carId", carId));
    }

    @Override
    public ArrayList<CarDto> findCarByUserId(final String userId) {
        List<CarDto> carDtos;
        carDtos = carMapper
                .carToDto(carRepository.findCarsByUserId(userId));

        return new ArrayList<>(carDtos);
    }

    @Override
    public Page<CarDto> findPaginatedSortedCar(final String carModel,final int page, final int size, final String[] sort) {
        List<Sort.Order> orders = sortingPagingUtils.getSortOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        List<CarDto> carDtos;
        if (carModel == null){
            carDtos = carMapper.carToDto(carRepository.findAll(pageable).toList());
        } else {
            carDtos = carMapper.carToDto(carRepository.findByCarModelContaining(carModel,pageable).toList());
        }
        return new PageImpl<>(carDtos);
    }

    @Override
    public void deleteCarById(final String carId) {
        Car car = carMapper.dtoToCar(findCarById(carId));
        car.setCarStatus(ModelStatus.INACTIVE);
        carRepository.save(car);
    }

    /**
     * Return Car if status code is ACTIVE.
     * @param car Car
     * @param queryField String
     * @param queryFieldValue String
     * @return User
     * @throws CarNotFoundException ex
     */
    private Car isActiveCar(Car car, String queryField, String queryFieldValue){
        if(car.getCarStatus().getStatusCode() == 0){
            return car;
        }
        throw CarNotFoundException
                .buildCarNotFoundExceptionForField(queryField, queryFieldValue);
    }

}
