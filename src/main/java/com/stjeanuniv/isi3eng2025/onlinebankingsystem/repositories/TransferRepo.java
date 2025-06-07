package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepo extends JpaRepository<Transfer, Integer> {
//    List<Transfer> findByDestinationAccount(Account destinationAccount);
//
//    List<Transfer> findBySourceAccount(Account sourceAccount);

    @Query("SELECT t FROM Transfer t WHERE t.DestinationAccount.id = :id")
    List<Transfer> findByDestinationAccount_Id(int id);

    @Query("SELECT t FROM Transfer t WHERE t.SourceAccount.id = :id")
    List<Transfer> findBySourceAccount_Id(int id);

}
