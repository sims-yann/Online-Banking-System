package com.stjeanuniv.isi3eng2025.onlinebankingsystem.services;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;

import java.util.List;
import java.util.Map;

public interface TransferService {

    public void recordTransfer(Transfer transfer);

    public void applyTransfer(Account sender, Account receiver, float amount);

    public boolean verifyAccountType(Account account);

    public boolean senderNotReceiver(Account sender, Account receiver);

    public boolean verifyBalance(Account sender, Float amount);

    public Map<String, Object> showTransferDetails(Transfer t);

    public List<Transfer> getTransferList();

    // Additional common transfer service methods
    public Transfer getTransferById(Long id);

    public List<Transfer> getTransfersByAccountId(Long accountId);

    public List<Transfer> getTransfersBySender(Account sender);

    public List<Transfer> getTransfersByReceiver(Account receiver);

    public void cancelTransfer(Long transferId);

    public boolean validateTransfer(Account sender, Account receiver, Float amount);
}