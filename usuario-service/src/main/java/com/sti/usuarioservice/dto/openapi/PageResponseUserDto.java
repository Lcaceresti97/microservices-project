package com.sti.usuarioservice.dto.openapi;

import com.sti.usuarioservice.dto.UserDto;
import com.sti.usuarioservice.dto.pageable.PageResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PageResponseUserDto")
public class PageResponseUserDto extends PageResponseDto<UserDto> {
}
