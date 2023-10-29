package com.example.springsecurityep3.controller;

import com.example.springsecurityep3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    JdbcUserDetailsManager jdbcUserDetailsManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/user")
    public String getUser() {
        return "user!";
    }

    @PostMapping("/add-user")
    public void addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        jdbcUserDetailsManager.createUser(user);
    }
}
