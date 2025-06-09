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



    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}