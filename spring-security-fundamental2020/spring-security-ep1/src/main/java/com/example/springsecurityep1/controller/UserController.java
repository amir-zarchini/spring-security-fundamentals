package com.example.springsecurityep1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("app")
public class UserController {

    @GetMapping("getUser")
    public String getUser(){
        return "user exist!";
    }
}
