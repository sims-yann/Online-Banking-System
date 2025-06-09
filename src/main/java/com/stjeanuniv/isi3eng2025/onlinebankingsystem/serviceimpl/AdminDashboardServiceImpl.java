package com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.AdminStatisticsDTO;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.TransactionStatus;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.AccountRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.TransactionRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.UserRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private TransactionRepo transactionRepo;

    @Override
    public AdminStatisticsDTO getDashboardStatistics() {
        AdminStatisticsDTO stats = new AdminStatisticsDTO();
        stats.setTotalUsers((int) userRepo.count());
        stats.setTotalAccounts((int) accountRepo.count());
        stats.setTotalTransactions((int) transactionRepo.count());
        stats.setActiveUsers((int) userRepo.countActiveUsers());
        stats.setInactiveUsers((int) userRepo.countInactiveUsers());
        stats.setInactiveAccounts((int) accountRepo.countSuspendedAccounts());
        stats.setActiveAccounts((int) accountRepo.countActiveAccounts());
        stats.setPendingTransactions((int) transactionRepo.countByStatus(TransactionStatus.PENDING));
        stats.setFailedTransactions((int) transactionRepo.countByStatus(TransactionStatus.FAILED));
        stats.setRecentUsers(userRepo.findTop5ByOrderByCreatedAtDesc());
        stats.setRecentTransactions(transactionRepo.findTop5ByOrderByCreatedAtDesc());

        return stats;
    }
}
