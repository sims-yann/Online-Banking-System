package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepo {
    void save(Transfer transfer);
}
