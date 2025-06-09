package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transaction;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminStatisticsDTO {
    private int totalUsers;
    private int activeUsers;
    private int inactiveUsers;
    private int totalAccounts;
    private int activeAccounts;
    private int inactiveAccounts;
    private int suspendedAccounts;
    private int totalTransactions;
    private int pendingTransactions;
    private int failedTransactions;
    private List<User> recentUsers;
    private List<Transaction> recentTransactions;
}
