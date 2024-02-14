package com.example.speedmeretrestex.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static com.example.speedmeretrestex.constants.Constants.DEFAULT_DISTANCE_KM_STAT;

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

    public float calcAverageSpeed() {
        long msDiff = timeIn.until(timeOut, ChronoUnit.MILLIS);
        long minutesDiff = TimeUnit.MILLISECONDS.toMinutes(msDiff);
        return DEFAULT_DISTANCE_KM_STAT / Float.valueOf(minutesDiff / 60F);
    }
}
