package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transaction;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.TransactionStatus;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryDTO {
    // Search criteria
    private String accountNumber;
    private TransactionType transactionType;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private TransactionStatus status;
    
    // Results
    private List<Transaction> transactions;
}