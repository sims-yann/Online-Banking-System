package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentTransactionDTO {
    public TransactionType type; // "Transfer", "Deposit", etc.
    public LocalDateTime date; // e.g. "May 1, 10:30 AM"
    public double amount;
    public TransactionStatus status;
}
