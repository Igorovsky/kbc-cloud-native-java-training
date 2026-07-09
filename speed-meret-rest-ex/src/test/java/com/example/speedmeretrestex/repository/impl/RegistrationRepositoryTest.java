package com.example.speedmeretrestex.repository.impl;

import com.example.speedmeretrestex.controller.model.RawRegistration;
import com.example.speedmeretrestex.repository.RegistrationRepositoryI;
import com.example.speedmeretrestex.repository.converter.RegistrationConverter;
import com.example.speedmeretrestex.repository.model.FormattedRegistration;
import com.example.speedmeretrestex.repository.model.Registration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RegistrationRepositoryTest {

    @MockBean
    RegistrationRepositoryI registrationRepositoryI;
    @MockBean
    RegistrationConverter registrationConverter;

    private AutoCloseable closeable;

    RegistrationRepository registrationRepository;

    RawRegistration rawRegistration = RawRegistration.builder().build();
    Registration registration = Registration.builder().build();


    @BeforeEach
    void init() {

        closeable = MockitoAnnotations.openMocks(this);
        registrationRepository = new RegistrationRepository(registrationRepositoryI, registrationConverter);
    }

    @Test
    void saveRegistration() {


        when(registrationConverter.convertToRegistration(rawRegistration)).thenReturn(registration);
        when(registrationRepositoryI.save(registration)).thenReturn(any());

        assertAll(() -> registrationRepository.saveRegistration(rawRegistration));
    }


    @Test
    void retrieveAllRegistrations() {

        FormattedRegistration formattedRegistration = FormattedRegistration.builder().build();

        when(registrationConverter.convertToFormattedRegistration(List.of(registration))).thenReturn(List.of(formattedRegistration));
        when(registrationRepositoryI.findAll()).thenReturn(List.of(registration));

        assertAll(() -> registrationRepository.retrieveAllRegistrations());
    }

}
