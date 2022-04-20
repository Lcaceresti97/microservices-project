package com.sti.carservice.service;

import com.sti.carservice.dto.CarDto;
import com.sti.carservice.exception.CarNotFoundException;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Service interface for User entity crud operations.
 * @author Laurent Caceres
 * @version 1.0.0
 */
public interface CarService {


    /**
     * Saves given Car into DB.
     * @param carDto Car
     */
    CarDto saveCar (CarDto carDto);


    /**
     * Find a Car by its ID.
     * @param carId String
     * @return car carDto
     */
    CarDto findCarById (final String carId) throws CarNotFoundException;

    /**
     * Find a Car by User its ID.
     * @param userId String
     * @return car carDto
     */
    ArrayList<CarDto> findCarByUserId(final String userId);

    /**
     * Return a page of sorted car.
     * @param carModel Car models to sort by.
     * @param page Page number to query by.
     * @param size Page size to query by.
     * @param sort Extra sort params to sort by.
     * @return PageResponseDto Car.
     */
    Page<CarDto> findPaginatedSortedCar(String carModel, int page, int size, String[] sort);

    /**
     * Delete car by its ID.
     * @param carId
     */
    void deleteCarById(final String carId);

}
