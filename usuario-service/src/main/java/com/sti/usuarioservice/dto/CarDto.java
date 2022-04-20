package com.sti.usuarioservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sti.usuarioservice.model.status.ModelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Car DTO class to encapsulate implementation of entity.
 * @author Laurent Caceres
 * @version 1.0.0
 */
@JsonSerialize
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {


    @JsonProperty
    @NotBlank
    @NotEmpty
    @Size(min = 5, max = 150)
    private String carMake;

    @JsonProperty
    @NotBlank
    @NotEmpty
    @Size(min = 5, max = 150)
    private String carModel;

    @JsonProperty
    private String userId;

    @JsonProperty("carStatus")
    private ModelStatus carStatus;

}
