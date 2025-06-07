package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.rest_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Admin")
public class AdminController {

    @GetMapping("/Dashboard")
    public String Dashboard(){
        return "/admin/admin-dashboard";
    }

    @GetMapping("/Settings")
    public String Settings(Model model) {
        return "/admin/admin-settings";
    }

    @GetMapping("/Transactions")
    public String Transactions(Model model) {
        return "/admin/admin-transaction";
    }

    @GetMapping("/Users")
    public String Users(Model model) {
        return "/admin/admin-users";
    }
}
