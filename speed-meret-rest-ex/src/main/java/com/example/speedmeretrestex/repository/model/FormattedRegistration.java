package com.example.speedmeretrestex.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormattedRegistration {

    private Long id;

    private String plateNumber;
    private LocalTime timeIn;
    private LocalTime timeOut;

}
