package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.api;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.AccountStatus;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.TransactionStatus;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.TransactionType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*") // Allow CORS for frontend testing
public class AdminDashboardController {

    @GetMapping("/statistics")
    public AdminStatisticsDTO getStatistics() {
        AdminStatisticsDTO stats = new AdminStatisticsDTO();
        stats.setTotalUsers(3);
        stats.setActiveUsers(3);
        stats.setInactiveUsers(0);
        stats.setTotalAccounts(4);
        stats.setActiveAccounts(4);
        stats.setSuspendedAccounts(0);
        stats.setTotalTransactions(10);
        stats.setPendingTransactions(0);
        stats.setFailedTransactions(0);
        return stats;
    }

    @GetMapping("/recent-users")
    public List<RecentUserDTO> getRecentUsers() {
        RecentUserDTO u1 = new RecentUserDTO();
        u1.name = "Admin User";
        u1.email = "admin@bankingsystem.com";
        u1.status = AccountStatus.ACTIVE;

        RecentUserDTO u2 = new RecentUserDTO();
        u2.name = "John Smith";
        u2.email = "john@example.com";
        u2.status = AccountStatus.ACTIVE;

        RecentUserDTO u3 = new RecentUserDTO();
        u3.name = "Jane Doe";
        u3.email = "jane@example.com";
        u3.status = AccountStatus.ACTIVE;

        return Arrays.asList(u1, u2, u3);
    }

    @GetMapping("/recent-transactions")
    public List<RecentTransactionDTO> getRecentTransactions() {
        RecentTransactionDTO t1 = new RecentTransactionDTO();
        t1.type = TransactionType.TRANSFER;
        t1.date = LocalDateTime.now();
        t1.amount = 500.00;
        t1.status = TransactionStatus.COMPLETED;

        RecentTransactionDTO t2 = new RecentTransactionDTO();
        t2.type = TransactionType.TRANSFER;
        t2.date = LocalDateTime.now();
        t2.amount = 125.50;
        t2.status = TransactionStatus.COMPLETED;

        RecentTransactionDTO t3 = new RecentTransactionDTO();
        t3.type = TransactionType.DEPOSIT;
        t3.date = LocalDateTime.now();
        t3.amount = 2500.00;
        t3.status = TransactionStatus.COMPLETED;

        return Arrays.asList(t1, t2, t3);
    }
}