package com.sti.carservice.model.mapper;

import com.sti.carservice.dto.CarDto;
import com.sti.carservice.model.Car;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {

    Car dtoToCar (CarDto dto);

    CarDto carToDto(Car car);

    List<Car> dtoToCar(List<CarDto> dtos);

    List<CarDto> carToDto(List<Car> cars);


}
