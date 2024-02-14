package com.example.speedmeretrestex.controller.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HighestSpeedVehicle {

    private String plate;
    private float avgSpeed;
    private int overtaken;
}
