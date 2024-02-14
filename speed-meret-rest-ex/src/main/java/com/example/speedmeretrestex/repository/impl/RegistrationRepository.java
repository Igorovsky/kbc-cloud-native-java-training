package com.example.speedmeretrestex.repository.impl;

import com.example.speedmeretrestex.controller.model.RawRegistration;
import com.example.speedmeretrestex.repository.RegistrationRepositoryI;
import com.example.speedmeretrestex.repository.converter.RegistrationConverter;
import com.example.speedmeretrestex.repository.model.FormattedRegistration;
import com.example.speedmeretrestex.repository.model.Registration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
@AllArgsConstructor
public class RegistrationRepository {

    RegistrationRepositoryI registrationRepositoryI;
    RegistrationConverter registrationConverter;

    public void saveRegistration(RawRegistration rawRegistration) {

        Registration reg = registrationConverter.convertToRegistration(rawRegistration);

        registrationRepositoryI.save(reg);
    }

    public List<FormattedRegistration> retrieveAllRegistrations() {

        List<Registration> regs = registrationRepositoryI.findAll();
        return registrationConverter.convertToFormattedRegistration(regs);
    }
}
