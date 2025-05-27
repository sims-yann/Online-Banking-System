package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controller;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.UserRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.AccountService;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.TransferService;
import org.springframework.security.access.prepost.PreAuthorize;
=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.User;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.UserRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.AccountService;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream

import java.util.List;
=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.SecurityPermission;
import java.util.List;
import java.util.Map;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

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

    @GetMapping("/transfer/{user}/history")
    @PreAuthorize("hasRole('User')")
    public String showTransfer(Model model, @PathVariable int user){

        List<Account> accounts = accountService.getAccount(user);
        List<Transfer> transfers = transferService.getTransferList(accounts);

        model.addAttribute("transfers", transfers);
        return "";
    }

    @GetMapping("/transfer")
    public String doTransfer(@ModelAttribute Transfer transfer, Model model){
        transferService.applyTransfer(transfer);

        model.addAttribute("Transfer", new Transfer());
        return "";
    }

}
