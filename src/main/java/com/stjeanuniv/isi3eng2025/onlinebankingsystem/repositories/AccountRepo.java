package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByUserId(Long userId);

    List<Account> findByStatus(AccountStatus status);
    
    Page<Account> findByAccountType(AccountType accountType, Pageable pageable);

    @Query("SELECT COUNT(a) FROM Account a WHERE a.status = :status")
    long countByStatus(AccountStatus status);

    @Query("SELECT COUNT(a) FROM Account a WHERE a.status = 'ACTIVE'")
    long countActiveAccounts();

    @Query("SELECT COUNT(a) FROM Account a WHERE a.status = 'SUSPENDED'")
    long countSuspendedAccounts();

    boolean existsByAccountNumber(String accountNumber);

    void delete(Account account);

    @Modifying
    @Query("UPDATE Account a set a.balance= a.balance + :amount WHERE a.id= :accountId")
    void updateAccountBalance(Long accountId, BigDecimal amount);

    @Modifying
    @Query("UPDATE Account a set a.status= :status WHERE a.id= :accountId")
    void updateAccountStatus(Long accountId, AccountStatus status);

    @Query("SELECT SUM(a.balance) FROM Account a WHERE a.user.id = :userId")
    BigDecimal getTotalBalanceByUserId(Long userId);
}
