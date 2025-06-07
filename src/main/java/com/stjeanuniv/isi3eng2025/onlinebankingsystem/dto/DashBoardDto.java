package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardDto {
    private long totalUsers;
    private long activeUsers;
    private long inactiveUsers;
    private long totalAccounts;
    private long activeAccounts;
    private long suspendedAccounts;
    private long totalTransactions;
    private long pendingTransactions;
    private long failedTransactions;
    private List<User> recentUsers;
    private List<Transaction> recentTransactions;

}
