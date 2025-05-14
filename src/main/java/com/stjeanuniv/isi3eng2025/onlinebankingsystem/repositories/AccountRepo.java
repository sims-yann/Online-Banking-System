package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo {
    void save(Account account);
}
