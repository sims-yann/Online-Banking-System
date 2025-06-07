package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByUserId(Long userId);

    @Query("SELECT COUNT(a) FROM Account a WHERE a.status = :status")
    long countByStatus(AccountStatus status);

    @Query("SELECT COUNT(a) FROM Account a WHERE a.status = 'ACTIVE'")
    long countActiveAccounts();

    @Query("SELECT COUNT(a) FROM Account a WHERE a.status = 'SUSPENDED'")
    long countSuspendedAccounts();

    boolean existsByAccountNumber(String accountNumber);

    void delete(Account account);
}
