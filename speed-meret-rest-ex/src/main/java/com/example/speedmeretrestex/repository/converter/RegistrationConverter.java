package com.example.speedmeretrestex.repository.converter;

import com.example.speedmeretrestex.controller.model.RawRegistration;
import com.example.speedmeretrestex.repository.model.FormattedRegistration;
import com.example.speedmeretrestex.repository.model.Registration;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.speedmeretrestex.constants.Constants.SPACE_SEPARATOR;

@Component
public class RegistrationConverter extends BaseConverter {

    public Registration convertToRegistration(RawRegistration rawRegistration) {

        return Registration.builder()
                .plateNumber(rawRegistration.getPlateNumber())
                .timeIn(rawRegistration.getTimeIn())
                .timeOut(rawRegistration.getTimeOut())
                .build();
    }

    public List<FormattedRegistration> convertToFormattedRegistration(List<Registration> regs) {

        return regs.stream()
                .map(registration -> FormattedRegistration.builder()
                                        .id(registration.getId())
                                        .plateNumber(registration.getPlateNumber())
                                        .timeIn(convertToLocalTime(registration.getTimeIn(), SPACE_SEPARATOR))
                                        .timeOut(convertToLocalTime(registration.getTimeOut(), SPACE_SEPARATOR))
                                        .build())
                .collect(Collectors.toList());
    }


}
