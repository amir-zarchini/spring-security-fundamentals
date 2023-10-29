package com.example.springsecurityep2.services;

import com.example.springsecurityep2.repositories.UserRepository;
import com.example.springsecurityep2.security.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JpaUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByUsername(username);
        return user.map(SecurityUser::new).orElseThrow(() -> new UsernameNotFoundException(username +" not found!"));
    }
}
