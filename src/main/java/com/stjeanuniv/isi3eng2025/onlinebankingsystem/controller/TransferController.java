package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controller;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.UserRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.AccountService;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.TransferService;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.SecurityPermission;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    TransferService transferService;
    AccountService accountService;
    UserRepo userRepo;

    public TransferController(TransferService transferService, AccountService accountService,
                              UserRepo userRepo){
        this.transferService = transferService;
        this.accountService = accountService;
        this.userRepo = userRepo;
    }

    @GetMapping("/{user}/history")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String showTransfer(Model model, @PathVariable int user){

        List<Account> accounts = accountService.getAccount(user);
        List<Transfer> transfers = transferService.getTransferList(accounts);

        model.addAttribute("transfers", transfers);
        return "";
    }

    @GetMapping("/history")
    @PreAuthorize("hasRole('ADMIN')")
    public String showHistory(Model model){
        model.addAttribute("history", transferService.getTransferList());

        return "";
    }

    @GetMapping("/create")
    public String createTransfer(Model model){
        model.addAttribute("transfer", new Transfer());

        return "";
    }



    @GetMapping("/ongoing")
    public String saveTransfer(@ModelAttribute Transfer transfer, Model model){
        transferService.applyTransfer(transfer);

        model.addAttribute("Transfer", new Transfer());
        return "";
    }

}
