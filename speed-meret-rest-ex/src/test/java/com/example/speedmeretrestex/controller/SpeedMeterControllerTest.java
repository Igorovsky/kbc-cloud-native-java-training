package com.example.speedmeretrestex.controller;

import com.example.speedmeretrestex.controller.model.RawRegistration;
import com.example.speedmeretrestex.repository.model.FormattedRegistration;
import com.example.speedmeretrestex.service.RegistrationServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpeedMeterController.class)
class SpeedMeterControllerTest {

    public static final String PLATE_NUM = "QPP263";
    public static final String TIME_IN = "8 0 5 0";
    public static final String TIME_OUT = "8 7 8 436";
    public static final long ID = 12L;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationServiceI registrationService;
    private AutoCloseable closeable;

    @BeforeEach
    void initService() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void ingestRegistration_positive_test() throws Exception {

        RawRegistration rawRegistrationInput = RawRegistration.builder()
                .plateNumber(PLATE_NUM)
                .timeIn(TIME_IN)
                .timeOut(TIME_OUT)
                .build();

        Mockito.doNothing().when(registrationService).ingestRegistration(rawRegistrationInput);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonInput = ow.writeValueAsString(rawRegistrationInput);

        this.mockMvc
                .perform(post("/speed-meter/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isOk());
    }

    @Test
    void retrieveAllRegistrations() throws Exception {

        List<FormattedRegistration> testResponseData = List.of(FormattedRegistration.builder()
                        .id(ID)
                        .plateNumber(PLATE_NUM)
                        .timeOut(LocalTime.of(8,20,0,0))
                        .timeIn(LocalTime.of(8,26,0,0))
                .build());

       when(registrationService.retrieveAllRegistrations())
               .thenReturn(testResponseData);


        this.mockMvc
                .perform(get("/speed-meter/registrations/list"))
                .andExpect(status().isOk());
    }

    @Test
    void retrieveAllRegistrationsStats() {
    }

    @Test
    void retrieveRegistrationsByTimeInterval() {
    }
}