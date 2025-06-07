package com.stjeanuniv.isi3eng2025.onlinebankingsystem.services;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transaction;
import java.util.List;
import java.math.BigDecimal;

public interface TransactionService {
 Transaction transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description) throws Exception;
    Transaction getTransactionById(Long id);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByDateRange(java.time.LocalDateTime start, java.time.LocalDateTime end);

}

