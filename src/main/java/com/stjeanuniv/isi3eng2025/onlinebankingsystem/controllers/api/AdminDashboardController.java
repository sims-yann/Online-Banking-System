package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.api;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*") // Allow CORS for frontend testing
public class AdminDashboardController {

    @GetMapping("/statistics")
    public AdminStatisticsDTO getStatistics() {
        AdminStatisticsDTO stats = new AdminStatisticsDTO();
        stats.totalUsers = 3;
        stats.activeUsers = 3;
        stats.inactiveUsers = 0;
        stats.totalAccounts = 4;
        stats.activeAccounts = 4;
        stats.suspendedAccounts = 0;
        stats.totalTransactions = 10;
        stats.pendingTransactions = 0;
        stats.failedTransactions = 0;
        return stats;
    }

    @GetMapping("/recent-users")
    public List<RecentUserDTO> getRecentUsers() {
        RecentUserDTO u1 = new RecentUserDTO();
        u1.name = "Admin User";
        u1.email = "admin@bankingsystem.com";
        u1.status = "active";

        RecentUserDTO u2 = new RecentUserDTO();
        u2.name = "John Smith";
        u2.email = "john@example.com";
        u2.status = "active";

        RecentUserDTO u3 = new RecentUserDTO();
        u3.name = "Jane Doe";
        u3.email = "jane@example.com";
        u3.status = "active";

        return Arrays.asList(u1, u2, u3);
    }

    @GetMapping("/recent-transactions")
    public List<RecentTransactionDTO> getRecentTransactions() {
        RecentTransactionDTO t1 = new RecentTransactionDTO();
        t1.type = "Transfer";
        t1.date = "May 1, 10:30 AM";
        t1.amount = 500.00;
        t1.status = "completed";

        RecentTransactionDTO t2 = new RecentTransactionDTO();
        t2.type = "Transfer";
        t2.date = "May 10, 06:45 PM";
        t2.amount = 125.50;
        t2.status = "completed";

        RecentTransactionDTO t3 = new RecentTransactionDTO();
        t3.type = "Deposit";
        t3.date = "May 15, 09:00 AM";
        t3.amount = 2500.00;
        t3.status = "completed";

        return Arrays.asList(t1, t2, t3);
    }
}