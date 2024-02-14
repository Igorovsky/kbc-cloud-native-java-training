package com.example.speedmeretrestex.controller.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegistrationsListAtTime {
    private int hour;
    private int minute;
}
