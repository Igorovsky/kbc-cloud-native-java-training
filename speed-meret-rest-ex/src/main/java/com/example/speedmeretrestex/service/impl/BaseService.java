package com.example.speedmeretrestex.service.impl;

import com.example.speedmeretrestex.controller.model.RawRegistration;
import com.example.speedmeretrestex.exceptions.BadRequestException;

import java.time.DateTimeException;
import java.time.LocalTime;

import static com.example.speedmeretrestex.constants.Constants.*;

public class BaseService {

    void validateTimeInputs(RawRegistration rawRegistration) throws BadRequestException {

        validateTime(TIME_IN_IS_NOT_VALID, rawRegistration.getTimeIn(), SPACE_SEPARATOR);
        validateTime(TIME_OUT_IS_NOT_VALID, rawRegistration.getTimeOut(), SPACE_SEPARATOR);

        // TODO move me to the fetch functionality

//        try {
//            String[] timeInRegistration = rawRegistration.getTimeIn().split(" ");
//            LocalTime.of(
//                    Integer.parseInt(timeInRegistration[0]),
//                    Integer.parseInt(timeInRegistration[1]),
//                    Integer.parseInt(timeInRegistration[2]),
//                    Integer.parseInt(timeInRegistration[3]));
//        } catch (DateTimeException e) {
//            throw new BadRequestException(TIME_IN_IS_NOT_VALID);
//        }
//
//        try {
//            String[] timeOutRegistration = rawRegistration.getTimeIn().split(" ");
//            LocalTime.of(
//                    Integer.parseInt(timeOutRegistration[0]),
//                    Integer.parseInt(timeOutRegistration[1]),
//                    Integer.parseInt(timeOutRegistration[2]),
//                    Integer.parseInt(timeOutRegistration[3]));
//        } catch (DateTimeException e) {
//            throw new BadRequestException(TIME_OUT_IS_NOT_VALID);
//        }
    }

    private void validateTime(String errPrefix, String time, String separator) {
        String[] timeRegistration = time.split(separator);

        validateHours(errPrefix, Integer.parseInt(timeRegistration[0]));
        validateMinutes(errPrefix, Integer.parseInt(timeRegistration[1]));
        validateSeconds(errPrefix, Integer.parseInt(timeRegistration[2]));
        validateMillis(errPrefix, Integer.parseInt(timeRegistration[3]));
    }

    private void validateHours(String errorPrefix, int hour) {
        if(hour > 23 || hour < 0) {
            throw new BadRequestException(errorPrefix + " " + TIME_HOUR_NOT_VALID);
        }
    }

    private void validateMinutes(String errorPrefix, int minute) {
        if(minute > 59 || minute < 0) {
            throw new BadRequestException(errorPrefix + " " + TIME_MINUTE_NOT_VALID);
        }
    }

    private void validateSeconds(String errorPrefix, int sec) {
        if(sec > 59 || sec < 0) {
            throw new BadRequestException(errorPrefix + " " + TIME_SECOND_NOT_VALID);
        }
    }

    private void validateMillis(String errorPrefix, int millis) {
        if(millis > 999 || millis < 0) {
            throw new BadRequestException(errorPrefix + " " + TIME_MILLIS_NOT_VALID);
        }
    }
}
