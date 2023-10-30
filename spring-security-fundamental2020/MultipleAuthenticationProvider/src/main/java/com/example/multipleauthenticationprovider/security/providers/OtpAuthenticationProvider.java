package com.example.multipleauthenticationprovider.security.providers;

import com.example.multipleauthenticationprovider.repositories.OtpRepository;
import com.example.multipleauthenticationprovider.security.authentications.OtpAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private OtpRepository otpRepository;
    private final String BAD_CREDENTIAL = "bad credential!";

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = authentication.getName();
        String otp = (String) authentication.getCredentials();

        var otpByUsername = otpRepository.findOtpByUsername(username);

        if (otpByUsername.isPresent()) {
            return new OtpAuthentication(username, otp, List.of(() -> "read"));
        }

        throw new BadCredentialsException(BAD_CREDENTIAL);
    }

    @Override
    public boolean supports(Class<?> classType) {
        return OtpAuthentication.class.equals(classType);
    }
}
