package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.transactionStatus = :status")
    long countByStatus(TransactionStatus status);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.transactionStatus = 'PENDING'")
    long countPendingTransactions();


    List<Transaction> findTransactionByFromAccountOrToAccount_Id(Long fromAccount, Long toAccountId);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.transactionStatus = 'FAILED'")
    long countFailedTransactions();

    @Query("SELECT t FROM Transaction t ORDER BY t.transactionDate DESC LIMIT 5")
    List<Transaction> findRecentTransactions();

    @Query("SELECT t FROM Transaction t WHERE t.description LIKE %:keyword% OR CAST(t.id AS string) LIKE %:keyword%")
    List<Transaction> searchTransactions(String keyword);

    @Query("SELECT t FROM Transaction t WHERE t.transactionDate BETWEEN :startDate AND :endDate")
    List<Transaction> findTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);


    List<Transaction> findByCreatedAtBetweenOrderByCreatedAtDesc(java.time.LocalDateTime start, java.time.LocalDateTime end);

    @Query("SELECT t FROM Transaction t WHERE "+"t.fromAccount.user.id = :userId OR t.toAccount.user.id = :userId " +"ORDER BY t.createdAt DESC")
    List<Transaction> findByFromAccountIdOrToAccountId(Long userId);

    @Query("SELECT t FROM Transaction t WHERE " +
           "(t.fromAccount.user.id = :userId OR t.toAccount.user.id = :userId) " +
           "AND t.createdAt >= :startDate")
    List<Transaction> findUserTransactionSince(Long userId, LocalDateTime since);

     @Query("SELECT COUNT(t) FROM Transaction t WHERE t.transactionStatus = 'COMPLETED'")
    Long countCompletedTransactions();

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE " +
           "t.transactionType = 'TRANSFER' AND t.transactionStatus = 'COMPLETED' " +
           "AND t.createdAt BETWEEN :startDate AND :endDate")
    BigDecimal getTotalTransfersBetween(LocalDateTime startOfDay, LocalDateTime now);

    List<Transaction> findTop5ByOrderByCreatedAtDesc();
}
