package com.api.DTOS;

import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingSpotDTO {
    @NotBlank
    private String responsibleName;

    @NotBlank
    private String block;

    @NotBlank
    private String apartment;

    @NotBlank
    private String licensePlateCar;

    @NotBlank
    private String modelCar;

    @NotBlank
    private String brandCar;

    @NotBlank
    private String colorCar;

}
