package com.example.speedmeretrestex.service;

import com.example.speedmeretrestex.controller.model.GeneralTrafficStatsResponse;
import com.example.speedmeretrestex.controller.model.RawRegistration;
import com.example.speedmeretrestex.controller.model.RegistrationsListAtTime;
import com.example.speedmeretrestex.controller.model.TrafficStatsResponse;
import com.example.speedmeretrestex.exceptions.BadRequestException;
import com.example.speedmeretrestex.repository.model.FormattedRegistration;

import java.util.List;

public interface RegistrationServiceI {
    void ingestRegistration(RawRegistration rawRegistration) throws BadRequestException;
    List<FormattedRegistration> retrieveAllRegistrations();

    List<FormattedRegistration> retrieveRegistrationsAt(RegistrationsListAtTime time);
    TrafficStatsResponse retrieveRegistrationsStatsAt(RegistrationsListAtTime time);

    GeneralTrafficStatsResponse retrieveAllRegistrationsStats();

}
