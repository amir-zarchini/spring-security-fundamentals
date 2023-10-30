package com.example.multipleauthenticationprovider.security.providers;

import com.example.multipleauthenticationprovider.security.authentications.TokenAuthentication;
import com.example.multipleauthenticationprovider.security.managers.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthProvider implements AuthenticationProvider {

    @Autowired
    private TokenManager tokenManager;
    private final String BAD_CREDENTIAL = "bad credential!";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();
        boolean exists = tokenManager.contains(token);

        if (exists) {
            return new TokenAuthentication(token, null, null);
        }

        throw new BadCredentialsException(BAD_CREDENTIAL);
    }

    @Override
    public boolean supports(Class<?> classType) {
        return TokenAuthentication.class.equals(classType);
    }
}
