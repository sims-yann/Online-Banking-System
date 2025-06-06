package com.stjeanuniv.isi3eng2025.onlinebankingsystem.service;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public interface TransferService {

    public void recordTransfer(Transfer transfer);
    public void applyTransfer(Transfer t);
    public boolean verifyAccountType(Account account);
    public boolean senderNotReceiver(Account sender, Account receiver, double amount);
    public boolean verifyBalance(Account sender, double amount);

    public Map<String, Object> showTransferDetails(Transfer t);

   public List<Transfer> getTransferList(List<Account> t);
    public List<Transfer> getTransferList();

    public LocalDate GetCurrentDate();

    public LocalTime GetCurrentTime();
}
