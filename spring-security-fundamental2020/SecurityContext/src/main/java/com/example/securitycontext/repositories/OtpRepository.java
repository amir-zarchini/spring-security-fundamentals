package com.example.securitycontext.repositories;

import com.example.securitycontext.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findOtpByUsername(String username);
}
