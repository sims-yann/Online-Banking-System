package com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.types.AccountStatus;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.types.AccountType;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.TransferRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        if(verify.getType().equals(AccountType.CURRENT) || verify.getStatus().equals(AccountStatus.ACTIVE)){
            return false;
        }
        return true;
    }

    //To verify that sender is not receiver
    public boolean senderNotReceiver(Account sender, Account receiver, double amount){
        if(sender.getId() == receiver.getId() || receiver.getStatus().equals(AccountStatus.BLOCK)){
            return false;
        }
        return true;
    }

    public void recordTransfer(Transfer transfer){
        transferRepo.save(transfer);
    }
}
