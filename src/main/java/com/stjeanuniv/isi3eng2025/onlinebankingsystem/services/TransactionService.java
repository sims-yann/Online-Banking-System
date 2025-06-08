package com.stjeanuniv.isi3eng2025.onlinebankingsystem.services;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.TransactionDTO;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transaction;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransactionService {
    Transaction transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description) throws Exception;

    Transaction getTransactionById(Long id);

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsByDateRange(java.time.LocalDateTime start, java.time.LocalDateTime end);

    List<Transaction> getRecentTransactions(int count);

    List<Transaction> getUserTransactions(Long userId, LocalDateTime since);

    long countAllTransactions();

    BigDecimal getDailyTransactionTotal(Long userId);

    boolean exceedsDailyLimit(Long userId, BigDecimal amount);

    Transaction createTransaction(TransactionDTO transactionDto);

    Transaction approveTransaction(Long transactionId);

    Transaction rejectTransaction(Long transactionId);
}

