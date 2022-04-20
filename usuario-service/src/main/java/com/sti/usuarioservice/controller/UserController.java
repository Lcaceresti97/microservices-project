package com.sti.usuarioservice.controller;

import com.sti.usuarioservice.dto.CarDto;
import com.sti.usuarioservice.dto.UserDto;
import com.sti.usuarioservice.dto.openapi.PageResponseUserDto;
import com.sti.usuarioservice.dto.openapi.ResponseUserDto;
import com.sti.usuarioservice.dto.pageable.PageResponse;
import com.sti.usuarioservice.dto.pageable.PageResponseDto;
import com.sti.usuarioservice.response.BaseResponse;
import com.sti.usuarioservice.response.Response;
import com.sti.usuarioservice.response.error.ErrorResponse;
import com.sti.usuarioservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Controller for User entity operations.
 * @author Laurent Caceres
 * @version 1.0.0
 */
@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Handler method for fetching a single User by its ID.
     * @param userId Integer
     * @return ResponseEntity User
     */
    @Operation(description = "Find a user by its ID.", operationId = "findUserById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"
                    , content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation =  UserDto.class))})
            , @ApiResponse(responseCode = "404", description = "User not found"
            , content = { @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping(value = "/{userId}")
    public ResponseEntity<? extends UserDto> findUserById(@PathVariable final String userId) {
        UserDto userDto = userService.findUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Get Paginated sorted user with given criteria.
     * @param userName String userName
     * @param page Page number
     * @param size page size
     * @param size page size
     * @param sort Sort params
     * @return ResponseEntity PageResponse UserDto
     */
    @Operation(summary = "Get a list of paginated/sorted users", operationId = "getUsers")
    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully."
            ,  content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
            , schema = @Schema(implementation = PageResponseUserDto.class))})
    @GetMapping(params = {"userName", "page", "size", "sort"})
    public ResponseEntity<? extends PageResponse<UserDto>> getUsers
    (@RequestParam(required = false) String userName,
     @RequestParam(defaultValue = "0")int page,
     @RequestParam(defaultValue = "5")int size,
     @RequestParam(defaultValue = "userId, desc")String[] sort){

        Page<UserDto> userDtoPage = userService.findPaginatedSortedUser(userName, page, size, sort);

        PageResponseDto<UserDto> pageResponseDto = new PageResponseDto<>();

        return pageResponseDto.buildResponseEntity(userDtoPage.getSize(), userDtoPage.getNumberOfElements(), userDtoPage.getTotalPages(), userDtoPage.getNumber(), userDtoPage.getContent());
    }

    /**
     * Handler method for fetching a single User by its ID.
     * @param userId String
     * @return ResponseEntity User
     */
    @Operation(description = "Find a user by its ID.", operationId = "getCarsByUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"
                    , content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation =  UserDto.class))})
            , @ApiResponse(responseCode = "404", description = "User not found"
            , content = { @Content(schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/cars/{userId}")
    public ResponseEntity<? extends Map<String, Object>> getCarsByUser(@PathVariable("userId") String userId) {

        Map<String, Object> getCars = userService.getCarsByUserId(userId);
        return ResponseEntity.ok(getCars);
    }

    /**
     * Handler method for saving Validated given user.
     * @param userDto userDto
     * @return ResponseEntity Response User
     */
    @Operation(summary = "Save given User.", operationId = "saveUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",  description = "User saved successfully"
                    ,content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation = ResponseUserDto.class))})
            , @ApiResponse(responseCode = "400", description = "Given User is invalid"
            , content = { @Content(schema = @Schema(implementation = ErrorResponse.class))})}
    )
    @PostMapping
    public ResponseEntity<? extends Response<UserDto>> saveUser(@RequestBody @Valid UserDto userDto) {
        UserDto saveUser = userService.saveUser(userDto);
        BaseResponse<UserDto> studentBaseResponse = new BaseResponse<>();
        return studentBaseResponse
                .buildResponseEntity(HttpStatus.CREATED, "User saved successfully", saveUser);
    }

    /**
     * Handler method for saving Validated given car by user.
     * @param carDto carDto
     * @return ResponseEntity Response Car
     */
    @Operation(summary = "Save given Car by User.", operationId = "saveCarByUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",  description = "Car by User saved successfully"
                    ,content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
                    , schema = @Schema(implementation = ResponseUserDto.class))})
            , @ApiResponse(responseCode = "400", description = "Given Car-User is invalid"
            , content = { @Content(schema = @Schema(implementation = ErrorResponse.class))})}
    )
    @PostMapping("/car/{userId}")
    public ResponseEntity<? extends Response<CarDto>> saveCarByUser (@PathVariable("userId") String userId, @RequestBody CarDto carDto){
        CarDto savedCarByUser = userService.saveCarByUser(userId, carDto);
        BaseResponse<CarDto> carDtoBaseResponse = new BaseResponse<>();
        return carDtoBaseResponse.buildResponseEntity(HttpStatus.CREATED,"Car By user saved succssfully", savedCarByUser);
    }

}
