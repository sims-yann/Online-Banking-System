package com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl;

import com.banking.entity.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import com.banking.service.TransactionService;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepo accountRepository;

    @Autowired
    private TransactionRepo transactionRepository;

    @Override
    @Transactional
    public Transaction transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description) throws Exception {
        // Validate accounts
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new Exception("Source account not found"));
        
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new Exception("Destination account not found"));

        // Validate amount
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Transfer amount must be greater than zero");
        }

        // Check sufficient balance
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new Exception("Insufficient balance in source account");
        }

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setTransactionType("TRANSFER");
        transaction.setStatus("PENDING");
        transaction.setDescription(description);

        try {
            // Update account balances
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));

            // Save updated accounts
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            // Update transaction status and save
            transaction.setStatus("COMPLETED");
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            transaction.setStatus("FAILED");
            transactionRepository.save(transaction);
            throw new Exception("Transfer failed: " + e.getMessage());
        }
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(java.time.LocalDateTime start, java.time.LocalDateTime end) {
        return transactionRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(start, end);
    }
} 