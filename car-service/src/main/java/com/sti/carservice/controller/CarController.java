package com.sti.carservice.controller;

import com.sti.carservice.dto.CarDto;
import com.sti.carservice.dto.openapi.PageResponseCarDto;
import com.sti.carservice.dto.openapi.ResponseCarDto;
import com.sti.carservice.dto.pageable.PageResponse;
import com.sti.carservice.dto.pageable.PageResponseDto;
import com.sti.carservice.response.BaseResponse;
import com.sti.carservice.response.Response;
import com.sti.carservice.response.error.ErrorResponse;
import com.sti.carservice.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for Car entity operations.
 * @author Laurent Caceres
 * @version 1.0.0
 */
@RestController
@RequestMapping(path = "/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    /**
     * Handler method for fetching a single Car by its ID.
     * @param carId String
     * @return ResponseEntity Car
     */
    @Operation(description = "Find a car by its ID.", operationId = "findCarById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car retrieved successfully"
                    , content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation =  CarDto.class))})
            , @ApiResponse(responseCode = "404", description = "Car not found"
            , content = { @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping(value = "/{carId}")
    public ResponseEntity<? extends CarDto> findByCarId(@PathVariable final String carId){
        CarDto carDto = carService.findCarById(carId);
        return new ResponseEntity<>(carDto, HttpStatus.OK);
    }

    /**
     * Handler method for fetching a single Car by its ID.
     * @param id String
     * @return ResponseEntity Car
     */
    @Operation(description = "Find a car by its ID.", operationId = "findCarByUserId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cars by User retrieved successfully"
                    , content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation =  CarDto.class))})
            , @ApiResponse(responseCode = "404", description = "Car not found"
            , content = { @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<List<CarDto>> listOfCarByUser(@PathVariable("userId") String id){
        List<CarDto> carDtoList = carService.findCarByUserId(id);
        if(carDtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carDtoList);
    }

    /**
     * Get Paginated sorted cars with given criteria.
     * @param carModel String carModel
     * @param page Page number
     * @param size page size
     * @param sort Sort params
     * @return ResponseEntity PageResponse CarDto
     */
    @Operation(summary = "Get a list of paginated/sorted cars", operationId = "getCars")
    @ApiResponse(responseCode = "200", description = "List of cars retrieved successfully."
            ,  content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(implementation = PageResponseCarDto.class))})
    @GetMapping(params = {"carModel", "page", "size", "sort"})
    public ResponseEntity<? extends PageResponse<CarDto>> getCars(
            @RequestParam(required = false) String carModel,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "carId, desc") String[] sort){

        Page<CarDto> carDtoPage = carService.findPaginatedSortedCar(carModel, page, size, sort);

        PageResponseDto<CarDto> pageResponseDto = new PageResponseDto<>();

        return  pageResponseDto.buildResponseEntity(carDtoPage.getSize(), carDtoPage.getNumberOfElements(), carDtoPage.getTotalPages(), carDtoPage.getNumber(), carDtoPage.getContent());
    }

    /**
     * Handler method for saving Validated given car.
     * @param carDto CarDto
     * @return ResponseEntity Response Car
     */
    @Operation(summary = "Save given car.", operationId = "saveCar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",  description = "Car saved successfully"
                    ,content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation = ResponseCarDto.class))})
            , @ApiResponse(responseCode = "400", description = "Given Car is invalid"
            , content = { @Content(schema = @Schema(implementation = ErrorResponse.class))})}
    )
    @PostMapping
    public ResponseEntity<? extends Response<CarDto>> saveCar(@RequestBody @Valid CarDto carDto){
        CarDto savedCar = carService.saveCar(carDto);
        BaseResponse<CarDto> carDtoBaseResponse = new BaseResponse<>();
        return carDtoBaseResponse.buildResponseEntity(HttpStatus.CREATED, "Car Saved Successfully", savedCar);
    }

    /**
     * Handler method for deleting a Car by its ID.
     * @param carId String
     * @return Response null
     */
    @Operation(description = "Delete a car by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car deleted successfully."
                    , content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation =  String.class))})
            , @ApiResponse(responseCode = "404", description = "Car not found"
            , content = { @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping(value = "/{carId}")
    public ResponseEntity<? extends Response<String>> deleteCar(@PathVariable final String carId) {
        carService.deleteCarById(carId);
        BaseResponse<String> carResponse = new BaseResponse<>();
        return carResponse
                .buildResponseEntity(HttpStatus.OK, new StringBuilder("Car with ID: ")
                        .append(carId)
                        .append(" was deleted.").toString(), carId);
    }
}
