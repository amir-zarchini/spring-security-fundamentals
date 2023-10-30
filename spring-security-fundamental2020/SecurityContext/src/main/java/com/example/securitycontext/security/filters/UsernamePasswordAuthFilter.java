package com.example.securitycontext.security.filters;

import com.example.securitycontext.entities.Otp;
import com.example.securitycontext.repositories.OtpRepository;
import com.example.securitycontext.security.authentications.OtpAuthentication;
import com.example.securitycontext.security.authentications.UsernamePasswordAuthentication;
import com.example.securitycontext.security.managers.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.UUID;

public class UsernamePasswordAuthFilter
        extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
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
            var token = UUID.randomUUID().toString();
            tokenManager.add(token);
            response.setHeader("Authorization", token);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }
}
