package com.example.speedmeretrestex.controller.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TrafficStatsResponse {
    private int totalRegistrations;
    private float intensity;
}
