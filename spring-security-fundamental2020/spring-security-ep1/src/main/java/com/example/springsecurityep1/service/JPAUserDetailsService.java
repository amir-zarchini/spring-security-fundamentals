package com.example.springsecurityep1.service;

import com.example.springsecurityep1.model.User;
import com.example.springsecurityep1.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JPAUserDetailsService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;


    /*
    چون این متد از جنس userDetails هستش ولی جنس ابجکت ما از user پس از کلاس SecurityUser
    استفاده میکنیم و آبجکت user خودمون رو بهش میدیم، چون securityUser یه impl از اینترفیس UserDetails هست
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("username not found: " + username));
        return new SecurityUser(user);
    }
}
