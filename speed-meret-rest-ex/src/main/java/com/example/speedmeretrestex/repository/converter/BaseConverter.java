package com.example.speedmeretrestex.repository.converter;

import java.time.LocalTime;

public class BaseConverter {

    LocalTime convertToLocalTime(String timeStr, String separator) {

        String[] timeRegistration = timeStr.split(separator);

        return LocalTime.of(Integer.parseInt(timeRegistration[0]),
                            Integer.parseInt(timeRegistration[1]),
                            Integer.parseInt(timeRegistration[2]),
                            Integer.parseInt(timeRegistration[3]));
    }
}
