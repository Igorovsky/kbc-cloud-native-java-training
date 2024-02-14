package com.example.speedmeretrestex.service;

import com.example.speedmeretrestex.controller.model.RawRegistration;
import com.example.speedmeretrestex.exceptions.BadRequestException;
import com.example.speedmeretrestex.repository.model.FormattedRegistration;
import com.example.speedmeretrestex.repository.model.Registration;

import java.util.Collection;
import java.util.List;

public interface RegistrationServiceI {
    void ingestRegistration(RawRegistration rawRegistration) throws BadRequestException;
    List<FormattedRegistration> retrieveAllRegistrations();

//    List<RawRegistration> getRegistrationsAt(String hour, String minute, List<RawRegistration> processedRawRegistrations);
//
//    float getIntensityAt(String hour, String minute, List<RawRegistration> processedRawRegistrations);
//
//    RawRegistration getHighestSpeedRegistration(List<RawRegistration> processedRawRegistrations);
//
//    int surpassedCountFor(RawRegistration highestSpeedRawRegistration, List<RawRegistration> processedRawRegistrations);
//
//    float getPercentSpeedingRegs(List<RawRegistration> processedRawRegistrations);
}
