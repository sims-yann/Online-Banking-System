package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {
    Account findByBalance(double balance);
}
