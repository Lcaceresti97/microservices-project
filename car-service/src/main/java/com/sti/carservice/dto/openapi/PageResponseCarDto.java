package com.sti.carservice.dto.openapi;


import com.sti.carservice.dto.CarDto;
import com.sti.carservice.dto.pageable.PageResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PageResponseCarDto")
public class PageResponseCarDto extends PageResponseDto<CarDto> {
}
