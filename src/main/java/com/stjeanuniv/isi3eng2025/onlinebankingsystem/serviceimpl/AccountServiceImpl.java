package com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.types.AccountStatus;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.AccountRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void recordAccount(Account account) {
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
       ac.setStatus(AccountStatus.BLOCK);
       accountRepo.save(ac);
    }

    public Map<String, Object> viewAccountDetails(int id){
        Account ac = accountRepo.findById(id);
        Map<String, Object> details =new HashMap<>();
        details.put("balace", ac.balance);
        details.put("createdDate", ac.CreatedDate);
        details.put("type", ac.type);
        details.put("status", ac.status);

        return details;
    }
}
