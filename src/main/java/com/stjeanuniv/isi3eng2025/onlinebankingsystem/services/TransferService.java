package com.stjeanuniv.isi3eng2025.onlinebankingsystem.services;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TransferService {

    public void recordTransfer(Transfer transfer);
    public void applyTransfer(Transfer t);
    public boolean verifyAccountType(Account account);
    public boolean senderNotReceiver(Account sender, Account receiver, double amount);
    public boolean verifyBalance(Account sender, double amount);

    public Map<String, Object> showTransferDetails(Transfer t);

   public List<Transfer> getTransferList(List<Account> t);
}
