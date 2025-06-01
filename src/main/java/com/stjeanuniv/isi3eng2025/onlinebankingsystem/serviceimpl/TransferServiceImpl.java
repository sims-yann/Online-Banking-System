package com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.TransferRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
public class TransferServiceImpl implements TransferService {

    private final AccountServiceImpl accountServiceImpl;

    private final TransferRepo transferRepo;

    TransferServiceImpl(TransferRepo transferRepo, AccountServiceImpl accountServiceImpl){
        this.transferRepo = transferRepo;
        this.accountServiceImpl = accountServiceImpl;
    }


    //To show a specific transfer detail
    public Map<String, Object> showTransferDetails(Transfer t){
        Map<String, Object> transferMetrics = new HashMap<>();

        transferMetrics.put("sender", t.getSourceAccount());
        transferMetrics.put("receiver", t.getDestinationAccount());
        transferMetrics.put("amount", t.getAmount());
        transferMetrics.put("date", t.getDate());
        transferMetrics.put("state", t.getStatus());

        return transferMetrics;
    }

    public List<Transfer> getTransferList() {
        return transferRepo.findAll();
    }

    @Override
    public LocalDate GetCurrentDate() {
        return LocalDate.now();
    }

    @Override
    public LocalTime GetCurrentTime() {
        return LocalTime.now();
    }

    //To tranfer an amount from a sender account to a receiver account
    public void applyTransfer(Transfer transfer){

        if(senderNotReceiver(transfer.getSourceAccount(), transfer.getDestinationAccount(), transfer.getAmount())){
            transfer.getSourceAccount().setBalance(transfer.getSourceAccount().getBalance() - transfer.getAmount());
            transfer.getDestinationAccount().setBalance(transfer.getDestinationAccount().getBalance() + transfer.getAmount());

            recordTransfer(transfer);

            accountServiceImpl.updateAccount(transfer.getSourceAccount());
            accountServiceImpl.updateAccount(transfer.getDestinationAccount());
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
