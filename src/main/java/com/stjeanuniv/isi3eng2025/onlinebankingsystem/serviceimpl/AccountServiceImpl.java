package com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.AccountRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private final AccountRepo accountRepo;

    public AccountServiceImpl( AccountRepo accountRepo){
        this.accountRepo = accountRepo;
    }

    //to update account
    public void updateAccount(Account account){
        accountRepo.save(account);
        accountRepo.flush();
    }

    public void CreateAccount(Account account) {
        accountRepo.save(account);
    }

    public void DeleteAccount(Account account) {
        accountRepo.delete(account);
    }

    public Account getAccount(int id) {
        return accountRepo.findById(id).get();
    }
}
