package com.stjeanuniv.isi3eng2025.onlinebankingsystem.services;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.AccountDto;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.AccountStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AccountService {
    //deactivating an account

    public void blockAccount(Long id);

    public Account updateAccount(Long accountId, AccountDto accountDto);

    public void recordAccount(Account account);

    public void DeleteAccount(Account account);

    Optional <Account> getAccount(Long id);
    public List<Account> getAllAccounts();

    public Map<String, Object> viewAccountDetails(Long id);

    Account createAccount(AccountDto accountDto);
    //Account updateAccount(Long accountId, AccountDto accountDto);
    void changeAccountStatus(Long accountId, AccountStatus status);
    Account getAccountById(Long accountId);
    Account getAccountByNumber(String accountNumber);
    List<Account> getUserAccounts(Long userId);
    long countAllAccounts();
    long countActiveAccounts();
    BigDecimal getTotalBalanceByUser(Long userId);
    String generateAccountNumber();

}
