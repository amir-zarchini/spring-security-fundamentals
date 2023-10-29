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

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = authentication.getName();
        String otp = (String) authentication.getCredentials();

        var o = otpRepository.findOtpByUsername(username);

        if (o.isPresent()) {
            return new OtpAuthentication(username, otp, List.of(() -> "read"));
        }

        throw new BadCredentialsException(":(");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return OtpAuthentication.class.equals(aClass);
    }
}
