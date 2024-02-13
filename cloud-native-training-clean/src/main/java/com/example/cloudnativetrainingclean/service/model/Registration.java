package com.example.cloudnativetrainingclean.service.model;

import com.example.cloudnativetrainingclean.constants.Constants;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Builder
@Getter
public class Registration {



    private String plateNumber;
    private LocalTime timeIn;
    private LocalTime timeOut;

    private float avgSpeed;

    public float calcAverageSpeed() {
        long msDiff = timeIn.until(timeOut, ChronoUnit.MILLIS);
        long minutesDiff = TimeUnit.MILLISECONDS.toMinutes(msDiff);
        return Constants.DEFAULT_DISTANCE_KM_STAT / Float.valueOf(minutesDiff / 60F);
    }
}
