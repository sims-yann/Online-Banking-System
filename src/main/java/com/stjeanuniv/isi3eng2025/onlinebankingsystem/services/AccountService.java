package com.stjeanuniv.isi3eng2025.onlinebankingsystem.services;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;

import java.util.List;
import java.util.Map;

public interface AccountService {

    public void updateAccount(Account account);

    //deactivating an account

    public void blockAccount(int id);

<<<<<<< Updated upstream
=======
    public void updateAccount(Account account);

>>>>>>> Stashed changes
    public void recordAccount(Account account);

    public void DeleteAccount(Account account);

    public List<Account> getAccount(int id);

    public Map<String, Object> viewAccountDetails(int id);

}
