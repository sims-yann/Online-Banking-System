package com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.AccountRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {


    private final AccountRepo accountRepo;

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

    public void recordAccount(Account account) {
    public void CreateAccount(Account account) {
        accountRepo.save(account);
    }

    public void DeleteAccount(Account account) {
        accountRepo.delete(account);
    }

    public List<Account> getAccount(int id) {
        return accountRepo.findByUserId(id);
    }

    //to block an account
    public void blockAccount(int id){
       Account ac = accountRepo.findById(id);

       accountRepo.save(ac);
    public Account getAccount(int id) {
        return accountRepo.findById(id).get();
    }

    public Map<String, Object> viewAccountDetails(int id){
        Account ac = accountRepo.findById(id);
        Map<String, Object> details =new HashMap<>();
        details.put("balace", ac.getBalance());
        details.put("createdDate", ac.getCreatedDate());
        details.put("type", ac.getType());
        details.put("status", ac.getStatus());

        return details;
    }

    //to save accounts

}
