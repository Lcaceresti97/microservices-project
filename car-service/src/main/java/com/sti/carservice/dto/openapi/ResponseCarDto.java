package com.sti.carservice.dto.openapi;


import com.sti.carservice.dto.CarDto;
import com.sti.carservice.response.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class used to define @Schema for carDto response wrapper in openapi documentation.
 */
@Schema(name = "ResponseCarDto")
public class ResponseCarDto extends BaseResponse<CarDto> {
}
