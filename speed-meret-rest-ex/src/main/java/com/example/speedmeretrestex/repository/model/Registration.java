package com.example.speedmeretrestex.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Registration {

    @Id
    @GeneratedValue
    private Long id;

    private String plateNumber;
    private String timeIn;
    private String timeOut;

}
