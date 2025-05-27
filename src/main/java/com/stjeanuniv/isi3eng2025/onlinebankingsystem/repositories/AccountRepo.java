package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo {
    void save(Account account);
    List<Account> findByUserId(int id);

    Account findById(int id);
    
    void flush();

    void delete(Account account);
}
