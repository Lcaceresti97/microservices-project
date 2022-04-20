package com.sti.usuarioservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sti.usuarioservice.model.status.ModelStatus;
import lombok.*;
import javax.validation.constraints.*;

/**
 * User DTO class to encapsulate implementation of entity.
 * @author Laurent Caceres
 * @version 1.0.0
 */
@JsonSerialize
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    @JsonProperty("userId")
    private String userId;

    @JsonProperty(required = true)
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 150)
    private String userName;

    @JsonProperty(required = true)
    @NotBlank
    @NotEmpty
    @Size(min = 4, max = 64)
    @Email(message = "User email must be valid")
    private String userEmail;

    @JsonProperty("userStatus")
    private ModelStatus userStatus;
}
