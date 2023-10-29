package com.example.multipleauthenticationprovider.security.filters;

import com.example.multipleauthenticationprovider.entities.Otp;
import com.example.multipleauthenticationprovider.repositories.OtpRepository;
import com.example.multipleauthenticationprovider.security.authentications.OtpAuthentication;
import com.example.multipleauthenticationprovider.security.authentications.UsernamePasswordAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsernamePasswordAuthFilter
        extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final OtpRepository otpRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Step 1: username & password
        // Step 2: username & otp

        var username = request.getHeader("username");
        var password = request.getHeader("password");
        var otp = request.getHeader("otp");

        if (otp == null) {
            // step 1: username and password
            Authentication authentication = new UsernamePasswordAuthentication(username, password);
            authentication = authenticationManager.authenticate(authentication);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            // we generate an OTP
            String code = String.valueOf(new Random().nextInt(9999) + 1000);

            Otp otpEntity = new Otp();
            otpEntity.setUsername(username);
            otpEntity.setOtp(code);
            otpRepository.save(otpEntity);
        } else {
            // step 2: username and otp
            Authentication authentication = new OtpAuthentication(username, otp);
            authentication = authenticationManager.authenticate(authentication);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            // we issue a token
            response.setHeader("Authorization", UUID.randomUUID().toString());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
