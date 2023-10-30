package com.example.multipleauthenticationprovider.service;

import com.example.multipleauthenticationprovider.entities.User;
import com.example.multipleauthenticationprovider.repositories.UserRepository;
import com.example.multipleauthenticationprovider.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    private final String BAD_CREDENTIAL = "bad credential!";

    @Override
    public UserDetails loadUserByUsername(String username) {
        var o = userRepository.findUserByUsername(username);
        User user = o.orElseThrow(() -> new UsernameNotFoundException(BAD_CREDENTIAL));
        return new SecurityUser(user);
    }
}
