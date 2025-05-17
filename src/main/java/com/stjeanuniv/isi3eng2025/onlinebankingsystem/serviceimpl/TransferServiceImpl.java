package com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.TransferRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TransferServiceImpl implements TransferService {

    private final AccountServiceImpl accountServiceImpl;

    @Autowired
    private final TransferRepo transferRepo;

    TransferServiceImpl(TransferRepo transferRepo, AccountServiceImpl accountServiceImpl){
        this.transferRepo = transferRepo;
        this.accountServiceImpl = accountServiceImpl;
    }


    //To show a specific transfer detail
    public Map<String, Object> showTransferDetails(Transfer t){
        Map<String, Object> transferMetrics = new HashMap<>();

        transferMetrics.put("sender", t.getSender());
        transferMetrics.put("receiver", t.getReceiver());
        transferMetrics.put("amount", t.getAmount());
        transferMetrics.put("date", t.getDate());
        transferMetrics.put("state", t.getState());

        return transferMetrics;
    }

    public List<Transfer> getTransferList() {
        return transferRepo.findAll();
    }

    //To tranfer an amount from a sender account to a receiver account
    public void applyTransfer(Transfer transfer){

        if(senderNotReceiver(transfer.getSender(), transfer.getReceiver(), transfer.getAmount())){
            transfer.getSender().setBalance(transfer.getSender().getBalance() - transfer.getAmount());
            transfer.getReceiver().setBalance(transfer.getReceiver().getBalance() + transfer.getAmount());

            recordTransfer(transfer);

            accountServiceImpl.updateAccount(transfer.getSender());
            accountServiceImpl.updateAccount(transfer.getReceiver());
        }

    }

    //To verify account balance is greater than amount
    public boolean verifyBalance(Account sender, double amount){
        if(sender.getBalance() >= amount){
            return verifyAccountType(sender);
        }
        return false;
    }

    //To verify the account type (current ou epargne)
    public boolean verifyAccountType(Account verify){
        if(verify.getAccountType().equals("CURRENT") ){
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
