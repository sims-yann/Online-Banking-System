package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {
    List<Account> findByUserId(int id);

    Account findById(int id);
    
    void flush();

    void delete(Account account);

    Account findByBalance(double balance);
}
