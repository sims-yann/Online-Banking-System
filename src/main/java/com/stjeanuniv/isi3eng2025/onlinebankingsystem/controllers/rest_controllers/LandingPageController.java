package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.rest_controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("")
public class LandingPageController {

    @GetMapping("/")
    public String landingpage(Model model) {
        return "index";
    }
}
