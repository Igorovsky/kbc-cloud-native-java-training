package com.example.speedmeretrestex.service.impl;


import com.example.speedmeretrestex.controller.model.GeneralTrafficStatsResponse;
import com.example.speedmeretrestex.controller.model.RawRegistration;
import com.example.speedmeretrestex.controller.model.RegistrationsListAtTime;
import com.example.speedmeretrestex.controller.model.TrafficStatsResponse;
import com.example.speedmeretrestex.exceptions.BadRequestException;
import com.example.speedmeretrestex.repository.impl.RegistrationRepository;
import com.example.speedmeretrestex.repository.model.FormattedRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RegistrationServiceTest {


    public static final String PLATE_NUM = "QPP263";
    public static final String TIME_IN = "8 0 5 0";
    public static final String TIME_OUT = "8 7 8 436";
    public static final long ID = 12L;
    public static final String INVALID_TIME_IN_HOURS = "25 0 5 0";
    public static final String INVALID_TIME_IN_MINUTES = "8 60 5 0";
    public static final String INVALID_TIME_IN_SECONDS = "8 0 60 0";
    public static final String INVALID_TIME_IN_MILLIS = "8 0 5 1000";

    RegistrationsListAtTime inputTimeAtNine = RegistrationsListAtTime.builder()
            .minute(0)
            .hour(9)
            .build();

    FormattedRegistration formattedRegAtEight = FormattedRegistration.builder()
            .id(ID)
            .timeIn(LocalTime.of(8, 10, 0 , 0))
            .timeOut(LocalTime.of(8, 30, 0 , 0))
            .build();

    FormattedRegistration formattedRegAtTen = FormattedRegistration.builder()
            .id(ID)
            .timeIn(LocalTime.of(10, 10, 0 , 0))
            .timeOut(LocalTime.of(10, 30, 0 , 0))
            .build();

    @MockBean
    private RegistrationRepository registrationRepository;

    private RegistrationService registrationServiceTest;

    private AutoCloseable closeable;

    @BeforeEach
    void initService() {

        closeable = MockitoAnnotations.openMocks(this);
        registrationServiceTest = new RegistrationService(registrationRepository);
    }

    @Test
    void ingestRegistration_valid_times_save() {

        RawRegistration rawRegistration = RawRegistration.builder()
                .plateNumber(PLATE_NUM)
                .timeIn(TIME_IN)
                .timeOut(TIME_OUT)
                .build();

        Mockito.doNothing().when(registrationRepository).saveRegistration(rawRegistration);

        assertDoesNotThrow(() -> registrationServiceTest.ingestRegistration(rawRegistration));

    }

    @Test
    void ingestRegistration_invalid_times_BadRequestException() {

        RawRegistration invalidRawRegistration = RawRegistration.builder()
                .plateNumber(PLATE_NUM)
                .timeIn(INVALID_TIME_IN_HOURS)
                .timeOut(TIME_OUT)
                .build();

        Mockito.doNothing().when(registrationRepository).saveRegistration(invalidRawRegistration);

        assertThrows(BadRequestException.class, () -> registrationServiceTest.ingestRegistration(invalidRawRegistration));
    }

    @Test
    void ingestRegistration_invalid_times_minutes_BadRequestException() {

        RawRegistration invalidRawRegistration = RawRegistration.builder()
                .plateNumber(PLATE_NUM)
                .timeIn(INVALID_TIME_IN_MINUTES)
                .timeOut(TIME_OUT)
                .build();

        Mockito.doNothing().when(registrationRepository).saveRegistration(invalidRawRegistration);

        assertThrows(BadRequestException.class, () -> registrationServiceTest.ingestRegistration(invalidRawRegistration));
    }

    @Test
    void ingestRegistration_invalid_times_seconds_BadRequestException() {
        RawRegistration invalidRawRegistration = RawRegistration.builder()
                .plateNumber(PLATE_NUM)
                .timeIn(INVALID_TIME_IN_SECONDS)
                .timeOut(TIME_OUT)
                .build();

        Mockito.doNothing().when(registrationRepository).saveRegistration(invalidRawRegistration);

        assertThrows(BadRequestException.class, () -> registrationServiceTest.ingestRegistration(invalidRawRegistration));
    }

    @Test
    void ingestRegistration_invalid_times_millis_BadRequestException() {

        RawRegistration invalidRawRegistration = RawRegistration.builder()
                .plateNumber(PLATE_NUM)
                .timeIn(INVALID_TIME_IN_MILLIS)
                .timeOut(TIME_OUT)
                .build();

        Mockito.doNothing().when(registrationRepository).saveRegistration(invalidRawRegistration);

        assertThrows(BadRequestException.class, () -> registrationServiceTest.ingestRegistration(invalidRawRegistration));
    }

    @Test
    void retrieveAllRegistrations_ok() {

        List<FormattedRegistration>  allRegsResponse = List.of(FormattedRegistration.builder().build());

        when(registrationRepository.retrieveAllRegistrations()).thenReturn(allRegsResponse);

        assertAll(() -> registrationServiceTest.retrieveAllRegistrations());
        assertEquals(registrationServiceTest.retrieveAllRegistrations(), allRegsResponse);
    }

    @Test
    void retrieveRegistrationsAt_returns_one_registration() {

        FormattedRegistration formattedRegOne = formattedRegAtEight;

        List<FormattedRegistration>  allRegsResponse = List.of(formattedRegOne);

        RegistrationsListAtTime inputTime = inputTimeAtNine;

        when(registrationRepository.retrieveAllRegistrations()).thenReturn(allRegsResponse);

        List<FormattedRegistration> output = registrationServiceTest.retrieveRegistrationsAt(inputTime);

        assertEquals(output.size(), 1);
        assertEquals(output.get(0).getId(), formattedRegOne.getId());
        assertEquals(output.get(0).getTimeIn(), formattedRegOne.getTimeIn());
        assertEquals(output.get(0).getTimeOut(), formattedRegOne.getTimeOut());
        assertEquals(output.get(0).getPlateNumber(), formattedRegOne.getPlateNumber());

    }

    @Test
    void retrieveRegistrationsAt_returns_no_registrations_before_time() {

        FormattedRegistration formattedRegOne = formattedRegAtTen;

        List<FormattedRegistration>  allRegsResponse = List.of(formattedRegOne);

        RegistrationsListAtTime inputTime = inputTimeAtNine;

        when(registrationRepository.retrieveAllRegistrations()).thenReturn(allRegsResponse);

        List<FormattedRegistration> output = registrationServiceTest.retrieveRegistrationsAt(inputTime);

        assertEquals(output.size(), 0);

    }

    @Test
    void retrieveRegistrationsStatsAt_one_registration() {

        FormattedRegistration formattedRegOne = formattedRegAtEight;
        FormattedRegistration formattedRegTwo = formattedRegAtTen;

        List<FormattedRegistration>  allRegsResponse = List.of(formattedRegOne, formattedRegTwo);

        RegistrationsListAtTime inputTime = inputTimeAtNine;

        when(registrationRepository.retrieveAllRegistrations()).thenReturn(allRegsResponse);

        TrafficStatsResponse output = registrationServiceTest.retrieveRegistrationsStatsAt(inputTime);

        assertNotNull(output);
        assertEquals(output.getTotalRegistrations(),1);
        assertEquals(output.getIntensity(),0.1F);
    }

    @Test
    void retrieveAllRegistrationsStats_ok() {

        // prepare
        FormattedRegistration formattedRegOne = formattedRegAtEight;
        FormattedRegistration formattedRegTwo = formattedRegAtTen;

        List<FormattedRegistration>  allRegsResponse = List.of(formattedRegOne, formattedRegTwo);

        when(registrationRepository.retrieveAllRegistrations()).thenReturn(allRegsResponse);

        // act
        GeneralTrafficStatsResponse output = registrationServiceTest.retrieveAllRegistrationsStats();

        // expect
        assertNotNull(output);
        assertEquals(output.getTotalRegistrationsBeforeNine(), 1);
        assertEquals(output.getHighestSpeedVehicle().getPlate(), formattedRegOne.getPlateNumber());
    }

}
