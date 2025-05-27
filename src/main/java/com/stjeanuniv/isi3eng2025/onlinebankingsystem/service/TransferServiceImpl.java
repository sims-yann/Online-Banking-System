package com.stjeanuniv.isi3eng2025.onlinebankingsystem.service;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.TransferRepo;
import org.springframework.beans.factory.annotation.Autowired;

<<<<<<< Updated upstream:src/main/java/com/stjeanuniv/isi3eng2025/onlinebankingsystem/service/TransferServiceImpl.java
import java.time.LocalDateTime;
import java.util.Arrays;
=======
import java.util.ArrayList;
>>>>>>> Stashed changes:src/main/java/com/stjeanuniv/isi3eng2025/onlinebankingsystem/serviceimpl/TransferServiceImpl.java
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TransferServiceImpl {

    @Autowired
    private final TransferRepo transferRepo;
    private final AccountService accountService;

    TransferServiceImpl(TransferRepo transferRepo, AccountService accountService){
        this.transferRepo = transferRepo;
        this.accountService = accountService;
    }


    //To show a specific transfer detail
    public Map<String, Object> showTransferDetails(Transfer t){
        Map<String, Object> transferMetrics = new HashMap<>();

        transferMetrics.put("code", t.getId());
        transferMetrics.put("sender", t.getSender());
        transferMetrics.put("receiver", t.getReceiver());
        transferMetrics.put("amount", t.getAmount());
        transferMetrics.put("date", t.getDate());
        transferMetrics.put("state", t.getState());

        return transferMetrics;
    }

<<<<<<< Updated upstream:src/main/java/com/stjeanuniv/isi3eng2025/onlinebankingsystem/service/TransferServiceImpl.java
    //To transfer an amount from a sender account to a receiver account
=======
    public List<Transfer> getTransferList(List<Account> accounts) {
        List<Transfer> transferList = new ArrayList<>();

        for (Account account : accounts) {
            // Find transfers where the account is either sender or receiver
            List<Transfer> senderTransfers = transferRepo.findBySender(account);
            List<Transfer> receiverTransfers = transferRepo.findByReceiver(account);

            transferList.addAll(senderTransfers);
            transferList.addAll(receiverTransfers);
        }

        // Remove duplicates if same transfer appears in both sender and receiver lists
        return transferList.stream().distinct().collect(Collectors.toList());
    }

    //To tranfer an amount from a sender account to a receiver account
>>>>>>> Stashed changes:src/main/java/com/stjeanuniv/isi3eng2025/onlinebankingsystem/serviceimpl/TransferServiceImpl.java
    public void applyTransfer(Transfer transfer){

        if(senderNotReceiver(transfer.getSender(), transfer.getReceiver(), transfer.getAmount())){
            transfer.getSender().setBalance(transfer.getSender().getBalance() - transfer.getAmount());
            transfer.getReceiver().setBalance(transfer.getReceiver().getBalance() + transfer.getAmount());

            recordTransfer(transfer);

            accountService.updateAccount(transfer.getSender());
            accountService.updateAccount(transfer.getReceiver());
        }

    }

    //To verify the account balance is greater than amount
    public boolean verifyBalance(Account sender, double amount){
        if(sender.getBalance() >= amount){
            return verifyAccountType(sender);
        }
        return false;
    }

    //To verify the account type (currant ou epargne)
    public boolean verifyAccountType(Account verify){
        if(verify.getAccountType().equals("CURRANT") ){
            return true;
        }
        return false;
    }

    //To verify that sender is not receiver
    public boolean senderNotReceiver(Account sender, Account receiver, double amount){
        if(sender.getId() != receiver.getId()){
            return verifyBalance(sender, amount)&&verifyAccountType(receiver);
        }
        return false;
    }

    public void recordTransfer(Transfer transfer){
        transferRepo.save(transfer);
    }
}
