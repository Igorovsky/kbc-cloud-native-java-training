package com.example.speedmeretrestex.service.impl;


import com.example.speedmeretrestex.controller.model.RawRegistration;
import com.example.speedmeretrestex.exceptions.BadRequestException;
import com.example.speedmeretrestex.repository.RegistrationRepositoryI;
import com.example.speedmeretrestex.repository.impl.RegistrationRepository;
import com.example.speedmeretrestex.repository.model.FormattedRegistration;
import com.example.speedmeretrestex.repository.model.Registration;
import com.example.speedmeretrestex.service.RegistrationServiceI;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Collection;


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


//
//    @Override
//    public List<Registration> getRegistrationsAt(String hour, String minute, List<Registration> processedRegistrations) {
//        return processedRegistrations.stream().map(
//                registration -> {
//                    LocalTime givenTime = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute), 0 , 0);
//                    if(registration.getTimeIn().isBefore(givenTime)) {
//                        return registration;
//                    }
//                    return null;
//                }).collect(Collectors.toList());
//    }
//
//    @Override
//    public float getIntensityAt(String hour, String minute, List<Registration> processedRegistrations) {
//        LocalTime givenTimeStart = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute), 0, 0);
//        LocalTime givenTimeEnd = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute), 59, 99);
//
//        List<Registration> processedRegsInPerMinute = processedRegistrations.stream()
//                .map( registration -> {
//                    if(registration.getTimeIn().isAfter(givenTimeStart) &&
//                        registration.getTimeOut().isBefore(givenTimeEnd)) {
//                            return registration;
//                    } else {
//                        return null;
//                    }
//                }).collect(Collectors.toList());
//
//        return ((float) processedRegsInPerMinute.size()) / DEFAULT_ROAD_SECTION_KM_STAT;
//    }
//
//    @Override
//    public Registration getHighestSpeedRegistration(List<Registration> processedRegistrations) {
//
//        HashMap<Float, Registration> processedAvgRegs = getProcessedAvgMap(processedRegistrations);
//
//        float maxSpeed = Collections.max(processedAvgRegs.keySet());
//        return processedAvgRegs.get(maxSpeed);
//    }
//
//    private HashMap<Float, Registration> getProcessedAvgMap(List<Registration> processedRegistrations) {
//        HashMap<Float, Registration> avgSpeedList = new HashMap<>();
//
//        processedRegistrations.stream()
//                .forEach(registration -> {
//                    float regAvg = registration.calcAverageSpeed();
//                    avgSpeedList.put(regAvg, registration);
//                });
//        return avgSpeedList;
//    }
//
//    @Override
//    public int surpassedCountFor(Registration highestSpeedRegistration, List<Registration> processedRegistrations) {
//        return processedRegistrations.stream()
//                .map(registration -> {
//                    if(registration.calcAverageSpeed() >= highestSpeedRegistration.calcAverageSpeed()) {
//                        return registration;
//                    } else {
//                        return null;
//                    }
//                }).filter(Objects::nonNull)
//                .collect(Collectors.toList())
//                .size();
//    }
//
//    @Override
//    public float getPercentSpeedingRegs(List<Registration> processedRegistrations) {
//        List<Registration> speedingRegs = processedRegistrations.stream()
//                .map(registration -> {
//                    if(registration.calcAverageSpeed() > 90F) {
//                        return registration;
//                    } else {
//                        return null;
//                    }
//                }).filter(Objects::nonNull)
//                .collect(Collectors.toList());
//
//        int speedingRegsCount = speedingRegs.size();
//        return (float) speedingRegsCount / (float)processedRegistrations.size() * 100;
//    }
}
