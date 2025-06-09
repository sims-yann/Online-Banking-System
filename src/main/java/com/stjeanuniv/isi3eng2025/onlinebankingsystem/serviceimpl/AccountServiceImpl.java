package com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.AccountDto;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.AccountStatus;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.User;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.exception.ResourceNotFoundException;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.AccountRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.UserRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private final AccountRepo accountRepo;
    @Autowired
    private final UserRepo userRepository;

    public AccountServiceImpl(AccountRepo accountRepo, UserRepo userRepo) {
        this.accountRepo = accountRepo;
        this.userRepository = userRepo;
    }

    //to update account
    @Override
    @Transactional
    public Account updateAccount(Long accountId, AccountDto accountDto) {

        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (accountDto.getAccountType() != null) {
            account.setAccountType(accountDto.getAccountType());
        }

        return accountRepo.save(account);
    }

    public void recordAccount(Account account) {
        accountRepo.save(account);
    }

    public void DeleteAccount(Account account) {
        accountRepo.delete(account);
    }

    @Override
    public Optional<Account> getAccount(Long id) {
        return accountRepo.findById(id);
    }

    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }


    //to block an account
    public void blockAccount(Long id) {
        Account ac = accountRepo.findById(id).get();
        accountRepo.save(ac);
    }

    @Override
    public Map<String, Object> viewAccountDetails(Long id) {
        Account ac = accountRepo.findById(id).get();
        Map<String, Object> details = new HashMap<>();
        details.put("balance", ac.getBalance());
        details.put("createdDate", ac.getCreatedAt());
        details.put("type", ac.getAccountType());
        details.put("status", ac.getStatus());

        return details;
    }

    @Override
    @Transactional
    public Account createAccount(AccountDto accountDto) {
        User user = userRepository.findById(accountDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Account account = new Account();
        account.setUser(user);
        account.setAccountType(accountDto.getAccountType());
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.INACTIVE);

        return accountRepo.save(account);
    }

    @Override
    @Transactional
    public void changeAccountStatus(Long accountId, AccountStatus status) {
        if (!accountRepo.existsById(accountId)) {
            throw new ResourceNotFoundException("Account not found");
        }
        accountRepo.updateAccountStatus(accountId, status);
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountRepo.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

    }

    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }

    @Override
    public List<Account> getUserAccounts(Long userId) {
        return accountRepo.findByUserId(userId);
    }

    @Override
    public long countAllAccounts() {
        return accountRepo.count();
    }

    @Override
    public long countActiveAccounts() {
        return accountRepo.countActiveAccounts();
    }

    @Override
    public BigDecimal getTotalBalanceByUser(Long userId) {
        return accountRepo.getTotalBalanceByUserId(userId);
    }

    public String generateAccountNumber() {
        Random random = new Random();
        long number = 1000000000L + random.nextInt(900000000);
        return String.valueOf(number);
    }

}
