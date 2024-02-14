package com.example.speedmeretrestex.repository;

import com.example.speedmeretrestex.controller.model.RawRegistration;
import com.example.speedmeretrestex.repository.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepositoryI extends JpaRepository<Registration, Long> {

}
