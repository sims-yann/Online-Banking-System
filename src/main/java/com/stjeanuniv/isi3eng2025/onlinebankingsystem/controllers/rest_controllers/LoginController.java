package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.rest_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "/auth/login";
    }

    @PostMapping("/register")
    public String register() {
        return "/auth/register";
    }
}
