package com.example.speedmeretrestex.service.impl;


import com.example.speedmeretrestex.controller.model.*;
import com.example.speedmeretrestex.exceptions.BadRequestException;
import com.example.speedmeretrestex.repository.RegistrationRepositoryI;
import com.example.speedmeretrestex.repository.impl.RegistrationRepository;
import com.example.speedmeretrestex.repository.model.FormattedRegistration;
import com.example.speedmeretrestex.repository.model.Registration;
import com.example.speedmeretrestex.service.RegistrationServiceI;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

import java.util.stream.Collectors;

import static com.example.speedmeretrestex.constants.Constants.DEFAULT_ROAD_SECTION_KM_STAT;
import static com.example.speedmeretrestex.constants.Constants.MAX_SPEED_LIMIT;


@Service
@AllArgsConstructor
public class RegistrationService extends BaseService implements RegistrationServiceI {

    private RegistrationRepository registrationRepository;


    @Override
    public void ingestRegistration(RawRegistration rawRegistration) throws BadRequestException {

        validateTimeInputs(rawRegistration);

        registrationRepository.saveRegistration(rawRegistration);
    }

    @Override
    public List<FormattedRegistration> retrieveAllRegistrations() {
        return registrationRepository.retrieveAllRegistrations();
    }

    @Override
    public List<FormattedRegistration> retrieveRegistrationsAt(RegistrationsListAtTime time) {
        List<FormattedRegistration> allRegs = registrationRepository.retrieveAllRegistrations();
        return allRegs.stream().map(
                registration -> {
                    LocalTime givenTime = LocalTime.of(time.getHour(), time.getMinute(), 0 , 0);
                    if(registration.getTimeIn().isBefore(givenTime)) {
                        return registration;
                    }
                    return null;
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public TrafficStatsResponse retrieveRegistrationsStatsAt(RegistrationsListAtTime time) {

        List<FormattedRegistration> allRegsAtTime = retrieveRegistrationsAt(time);

        LocalTime givenTimeStart = LocalTime.of(time.getHour(), time.getMinute(), 0, 0);
        LocalTime givenTimeEnd = LocalTime.of(time.getHour(), time.getMinute(), 59, 99);

        List<FormattedRegistration> processedRegsInPerMinute = allRegsAtTime.stream()
                .map( registration -> {
                    if(registration.getTimeIn().isAfter(givenTimeStart) &&
                            registration.getTimeOut().isBefore(givenTimeEnd)) {
                        return registration;
                    } else {
                        return null;
                    }
                }).collect(Collectors.toList());

        return TrafficStatsResponse.builder()
                .totalRegistrations(allRegsAtTime.size())
                .intensity(((float) processedRegsInPerMinute.size()) / DEFAULT_ROAD_SECTION_KM_STAT)
                .build();
    }

    @Override
    public GeneralTrafficStatsResponse retrieveAllRegistrationsStats() {

        List<FormattedRegistration> allRegs = registrationRepository.retrieveAllRegistrations();

        List<FormattedRegistration> allRegsBeforeNine = allRegs.stream().map(
                        registration -> {
                            LocalTime givenTime = LocalTime.of(9, 9, 0 , 0);
                            if(registration.getTimeIn().isBefore(givenTime)) {
                                return registration;
                            }
                            return null;
                        }).filter(Objects::nonNull)
                .collect(Collectors.toList());

        HashMap<Float, FormattedRegistration> processedAvgRegs = getProcessedAvgMap(allRegs);

        float maxSpeed = Collections.max(processedAvgRegs.keySet());

        FormattedRegistration highestSpeedVehicle = processedAvgRegs.get(maxSpeed);

        int overtaken = allRegs.stream()
                .map(registration -> {
                    float regAvg = registration.calcAverageSpeed();
                    if(maxSpeed > regAvg) {
                        return registration;
                    } else {
                        return null;
                    }
                }).filter(Objects::nonNull)
                .collect(Collectors.toList())
                .size();

        List<FormattedRegistration> speedingRegs = allRegs.stream()
                .map(registration -> {
                    if(registration.calcAverageSpeed() > MAX_SPEED_LIMIT) {
                        return registration;
                    } else {
                        return null;
                    }
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());

        int speedingRegsCount = speedingRegs.size();

        return GeneralTrafficStatsResponse.builder()
                .highestSpeedVehicle(
                        HighestSpeedVehicle.builder()
                                .avgSpeed(highestSpeedVehicle.calcAverageSpeed())
                                .plate(highestSpeedVehicle.getPlateNumber())
                                .overtaken(overtaken)
                                .build()
                )
                .totalRegistrations(allRegs.size())
                .totalRegistrationsBeforeNine(allRegsBeforeNine.size())
                .percentSpeedingVehicles((float) speedingRegsCount / (float)allRegs.size() * 100)
                .build();
    }

    private HashMap<Float, FormattedRegistration> getProcessedAvgMap(List<FormattedRegistration> processedRegistrations) {
        HashMap<Float, FormattedRegistration> avgSpeedList = new HashMap<>();

        processedRegistrations.stream()
                .forEach(registration -> {
                    float regAvg = registration.calcAverageSpeed();
                    avgSpeedList.put(regAvg, registration);
                });
        return avgSpeedList;
    }


}
