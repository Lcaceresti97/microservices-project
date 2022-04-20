package com.sti.usuarioservice.feignclients;

import com.sti.usuarioservice.dto.CarDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "car-service", url = "http://localhost:8002/api/v1")
@RequestMapping("/cars")
public interface CarFeignClient {

    @PostMapping
    public CarDto saveCar(@RequestBody CarDto carDto);

    @GetMapping("/users/{userId}")
    public List<CarDto> getCarsByUser(@PathVariable("userId") String userId);
}
