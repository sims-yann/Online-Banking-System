package com.stjeanuniv.isi3eng2025.onlinebankingsystem.service;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TransferService {

    public void recordTransfer(Transfer transfer);
    public void applyTransfer(Account sender, Account receiver, float amount);
    public boolean verifyAccountType(Account account);
    public boolean senderNotReceiver(Account sender, Account receiver);
    public boolean verifyBalance(Account sender, Float amount);

    public Map<String, Object> showTransferDetails(Transfer t);

    public List<Transfer> getTransferList();

    public void
}
