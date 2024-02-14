package com.example.speedmeretrestex.controller.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GeneralTrafficStatsResponse {

    private HighestSpeedVehicle highestSpeedVehicle;
    private float percentSpeedingVehicles;
    private int totalRegistrations;
    private int totalRegistrationsBeforeNine;   // Note: This is part of the original exercise, but it could be refactored into an object later.
}
