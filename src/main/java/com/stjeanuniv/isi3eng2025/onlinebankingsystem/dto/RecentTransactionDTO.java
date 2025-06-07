package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;

public class RecentTransactionDTO {
    public TransactionType type; // "Transfer", "Deposit", etc.
    public LocalDateTime date; // e.g. "May 1, 10:30 AM"
    public double amount;
    public TransactionStatus status;
}
