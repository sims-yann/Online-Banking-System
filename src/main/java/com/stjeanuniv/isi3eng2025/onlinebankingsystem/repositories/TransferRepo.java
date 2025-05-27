package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
<<<<<<< Updated upstream
public interface TransferRepo {
    void save(Transfer transfer);
=======
public interface TransferRepo extends JpaRepository<Transfer, Integer> {
    List<Transfer> findBySender(Account a);
    List<Transfer> findByReceiver(Account a);

>>>>>>> Stashed changes
}
