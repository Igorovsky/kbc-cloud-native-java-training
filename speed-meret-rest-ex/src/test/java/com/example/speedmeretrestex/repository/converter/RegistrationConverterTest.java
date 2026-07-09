package com.example.speedmeretrestex.repository.converter;

import com.example.speedmeretrestex.controller.model.RawRegistration;
import com.example.speedmeretrestex.repository.model.FormattedRegistration;
import com.example.speedmeretrestex.repository.model.Registration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RegistrationConverterTest {

    public static final long ID = 12L;
    public static final String PLATE_NUM = "QPP263";
    public static final String TIME_IN = "8 0 5 0";
    public static final String TIME_OUT = "8 7 8 436";

    @Autowired
    RegistrationConverter registrationConverter;

    RawRegistration rawRegistration = RawRegistration.builder()
            .plateNumber(PLATE_NUM)
            .timeIn(TIME_IN)
            .timeOut(TIME_OUT)
            .build();

    @BeforeEach
    void init() {
        registrationConverter = new RegistrationConverter();
    }

    @Test
    void convertToRegistration() {

        // act
        Registration output = registrationConverter.convertToRegistration(rawRegistration);

        // expect
        assertEquals(output.getPlateNumber(), rawRegistration.getPlateNumber());
    }

    @Test
    void convertToFormattedRegistration() {

        // prepare
        Registration regOne = Registration.builder()
                .id(ID)
                .plateNumber(PLATE_NUM)
                .timeIn(TIME_IN)
                .timeOut(TIME_OUT)
                .build();

        List<Registration> regs = List.of(regOne);

        // act
        List<FormattedRegistration> output = registrationConverter.convertToFormattedRegistration(regs);

        // expect
        assertEquals(output.size(), regs.size());
    }
}
