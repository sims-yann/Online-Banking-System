package com.stjeanuniv.isi3eng2025.onlinebankingsystem.service;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountServiceImpl {

    @Autowired
    private final AccountRepo accountRepo;

    public AccountServiceImpl( AccountRepo accountRepo){
        this.accountRepo = accountRepo;
    }

    //to save accounts
    public void updateAccount(Account account){
        accountRepo.save(account);
    }
}
