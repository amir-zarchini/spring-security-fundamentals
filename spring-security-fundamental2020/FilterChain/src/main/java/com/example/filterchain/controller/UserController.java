package com.example.filterchain.controller;


@RestController
public class UserController {

    @GetMapping("/user")
    public String getUser() {
        return "user!";
    }

}