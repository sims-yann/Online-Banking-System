package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("/dashboard")
    public String Dashboard() {
        return "/Customer/user-dashboard";
    }

    @GetMapping("/crofile")
    public String Profile(Model model) {
        return "/Customer/profile";
    }

    @GetMapping("/transactions")
    public String Transactions(Model model) {
        return "/Customer/transactions";
    }

    @GetMapping("/accounts")
    public String Accounts(Model model) {
        return "/Customer/accounts";
    }

    @GetMapping("/transfer")
    public String Transfer(Model model) {
        return "/Customer/transfer";
    }

    @GetMapping("/deposit-Withdraw")
    public String DepositWithdraw(Model model) {
        return "/Customer/deposit-withdraw";
    }
}
