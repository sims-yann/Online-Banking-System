package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controller;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/account/create")
    public String createAccount(@ModelAttribute Account account){
        accountService.recordAccount(account);

        return "";
    }
}
