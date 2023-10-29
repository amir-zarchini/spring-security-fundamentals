package com.example.multipleauthenticationprovider.service;

import com.example.multipleauthenticationprovider.entities.User;
import com.example.multipleauthenticationprovider.repositories.UserRepository;
import com.example.multipleauthenticationprovider.security.model.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var o = userRepository.findUserByUsername(username);
        User user = o.orElseThrow(() -> new UsernameNotFoundException("username not found"));
        return new SecurityUser(user);
    }
}
