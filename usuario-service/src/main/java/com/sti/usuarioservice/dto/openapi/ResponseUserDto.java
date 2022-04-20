package com.sti.usuarioservice.dto.openapi;

import com.sti.usuarioservice.dto.UserDto;
import com.sti.usuarioservice.response.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class used to define @Schema for UserDto response wrapper in openapi documentation.
 */
@Schema(name = "ResponseUserDto")
public class ResponseUserDto extends BaseResponse<UserDto> {
}
