package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.transactionStatus = :status")
    List<Transaction> countByStatus(TransactionStatus status);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.transactionStatus = 'PENDING'")
    List<Transaction> countPendingTransactions();

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.transactionStatus = 'FAILED'")
    List<Transaction> countFailedTransactions();

    @Query("SELECT t FROM Transaction t ORDER BY t.transactionDate DESC LIMIT 5")
    List<Transaction> findRecentTransactions();

    @Query("SELECT t FROM Transaction t WHERE t.description LIKE %:keyword% OR CAST(t.id AS string) LIKE %:keyword%")
    List<Transaction> searchTransactions(String keyword);

    @Query("SELECT t FROM Transaction t WHERE t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);


    List<Transaction> findByCreatedAtBetweenOrderByCreatedAtDesc(java.time.LocalDateTime start, java.time.LocalDateTime end);

    List<Transaction> findByFromAccountIdOrToAccountId(Long fromAccountId, Long toAccountId);

}
