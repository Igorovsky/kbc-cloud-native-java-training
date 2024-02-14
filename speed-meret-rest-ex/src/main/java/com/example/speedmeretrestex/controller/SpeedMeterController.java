package com.example.speedmeretrestex.controller;

import com.example.speedmeretrestex.controller.model.*;
import com.example.speedmeretrestex.repository.model.FormattedRegistration;
import com.example.speedmeretrestex.repository.model.Registration;
import com.example.speedmeretrestex.service.RegistrationServiceI;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static com.example.speedmeretrestex.constants.Constants.REGISTRATION_INGESTED_RESPONSE_MESSAGE;

@RestController
@RequestMapping("/speed-meter")
@AllArgsConstructor
public class SpeedMeterController {

    RegistrationServiceI registrationService;


    @PostMapping(value = "/registration")
    public ResponseEntity<StatusResponse> ingestRegistration(@Valid @RequestBody RawRegistration rawRegistration) {

        registrationService.ingestRegistration(rawRegistration);

        return ResponseEntity.ok(StatusResponse.builder().code(200).message(REGISTRATION_INGESTED_RESPONSE_MESSAGE).build());
    }

    @GetMapping(value = "/registrations/list")
    public ResponseEntity<List<FormattedRegistration>> retrieveAllRegistrations() {

        List<FormattedRegistration> regs = registrationService.retrieveAllRegistrations();

        return ResponseEntity.ok(regs);
    }

    @GetMapping(value = "/registrations/stats")
    public ResponseEntity<GeneralTrafficStatsResponse> retrieveAllRegistrationsStats() {

        GeneralTrafficStatsResponse resp = registrationService.retrieveAllRegistrationsStats();

        return ResponseEntity.ok(resp);
    }

    @PostMapping(value = "/registrations/list")
    public ResponseEntity<TrafficStatsResponse> retrieveRegistrationsByTimeInterval(
            @Valid @RequestBody RegistrationsListAtTime time) {

        TrafficStatsResponse resp = registrationService.retrieveRegistrationsStatsAt(time);

        return ResponseEntity.ok(resp);
    }

}
