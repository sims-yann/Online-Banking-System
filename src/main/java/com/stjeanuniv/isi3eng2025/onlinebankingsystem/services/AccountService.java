package com.stjeanuniv.isi3eng2025.onlinebankingsystem.services;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;

public interface AccountService {

    public void updateAccount(Account account);

    public void CreateAccount(Account account);

    public void DeleteAccount(Account account);

    public Account getAccount(int id);
}
