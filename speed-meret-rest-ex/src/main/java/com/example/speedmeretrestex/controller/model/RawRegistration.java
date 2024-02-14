package com.example.speedmeretrestex.controller.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RawRegistration {

    @NotBlank(message = "Plate number is mandatory")
    private String plateNumber;
    @NotBlank(message = "Time in is mandatory")
    private String timeIn;
    @NotBlank(message = "Time in is mandatory")
    private String timeOut;

}
