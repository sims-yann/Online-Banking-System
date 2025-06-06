package com.stjeanuniv.isi3eng2025.onlinebankingsystem.services;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;

import java.util.List;
import java.util.Map;

public interface AccountService {
    //deactivating an account

    public void blockAccount(int id);

    public void updateAccount(Account account);

    public void CreateAccount(Account account);
    public void recordAccount(Account account);

    public void DeleteAccount(Account account);

    public Account getAccount(int id);
    public List<Account> getAccount(int id);

    public Map<String, Object> viewAccountDetails(int id);

}
