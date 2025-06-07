package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.rest_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/Customer")
public class CustomerController {

    @GetMapping("/Dashboard")
    public String Dashboard() {
        return "/Customer/user-dashboard";
    }

    @GetMapping("/Profile")
    public String Profile(Model model) {
        return "/Customer/profile";
    }

    @GetMapping("/Transactions")
    public String Transactions(Model model) {
        return "/Customer/transactions";
    }

    @GetMapping("/Accounts")
    public String Accounts(Model model) {
        return "/Customer/accounts";
    }

    @GetMapping("/Transfer")
    public String Transfer(Model model) {
        return "/Customer/transfer";
    }

    @GetMapping("/Deposit-Withdraw")
    public String DepositWithdraw(Model model) {
        return "/Customer/deposit-withdraw";
    }
}
