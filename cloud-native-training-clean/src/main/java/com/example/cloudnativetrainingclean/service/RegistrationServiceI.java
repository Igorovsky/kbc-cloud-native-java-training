package com.example.cloudnativetrainingclean.service;

import com.example.cloudnativetrainingclean.service.model.Registration;

import java.util.List;

public interface RegistrationServiceI {

    List<Registration> getRegistrationsAt(String hour, String minute, List<Registration> processedRegistrations);

    float getIntensityAt(String hour, String minute, List<Registration> processedRegistrations);

    Registration getHighestSpeedRegistration(List<Registration> processedRegistrations);

    int surpassedCountFor(Registration highestSpeedRegistration, List<Registration> processedRegistrations);

    float getPercentSpeedingRegs(List<Registration> processedRegistrations);
}
