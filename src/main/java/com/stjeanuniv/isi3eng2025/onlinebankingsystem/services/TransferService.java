package com.stjeanuniv.isi3eng2025.onlinebankingsystem.services;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public interface TransferService {

    public void recordTransfer(Transfer transfer);
    public void applyTransfer(Transfer transfer);
    public boolean verifyAccountType(Account account);
    public boolean senderNotReceiver(Account sender, Account receiver, double amount);
    public boolean verifyBalance(Account sender, double amount);

    public Map<String, Object> showTransferDetails(Transfer t);

    public List<Transfer> getTransferList();

    public LocalDate GetCurrentDate();

    public LocalTime GetCurrentTime();
}
