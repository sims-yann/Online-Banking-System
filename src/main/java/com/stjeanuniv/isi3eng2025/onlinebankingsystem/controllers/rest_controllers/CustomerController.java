package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.rest_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Customer")
public class CustomerController {

    @GetMapping("/Dashboard")
    public String Dashboard() {
        return "/Customer/user-dashboard";
    }
}
