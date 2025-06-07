package com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.CustomerRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.TransferRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.UserRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.AccountService;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransferServiceImpl implements TransferService {

    private final AccountService accountService;

    @Autowired
    private final TransferRepo transferRepo;

    @Autowired
    private JavaMailSender mailSender;

    private final CustomerRepo userRepo;

    public TransferServiceImpl(TransferRepo transferRepo, AccountService accountService,
                               CustomerRepo userRepo){
        this.transferRepo = transferRepo;
        this.accountService = accountService;
        this.userRepo = userRepo;
    }

    //To show a specific transfer detail
    public Map<String, Object> showTransferDetails(Transfer t){
        Map<String, Object> transferMetrics = new HashMap<>();

        transferMetrics.put("sender", t.getSender());
        transferMetrics.put("receiver", t.getReceiver());
        transferMetrics.put("amount", t.getAmount());
        transferMetrics.put("date", t.getTime());
        transferMetrics.put("state", t.getState()); //Completed or Rejected

        return transferMetrics;
    }

    public List<Transfer> getTransferList() {
        return transferRepo.findAll();
    }

    public List<Transfer> getTransferList(List<Account> accounts) {
        List<Transfer> transferList = new ArrayList<>();

        for (Account account : accounts) {
            // Find transfers where the account is either sender or receiver
            List<Transfer> senderTransfers = transferRepo.findBySourceAccount(account);
            List<Transfer> receiverTransfers = transferRepo.findByDestinationAccount(account);

            transferList.addAll(senderTransfers);
            transferList.addAll(receiverTransfers);
        }

        // Remove duplicates if same transfer appears in both sender and receiver lists
        return transferList.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public LocalTime GetCurrentTime() {
        return LocalTime.now();
    }

    @Override
    public LocalDate GetCurrentDate() {
        return LocalDate.now();
    }

    //To transfer an amount from a sender account to a receiver account
    public void applyTransfer(Transfer transfer){
        if(senderNotReceiver(transfer.getSender(), transfer.getReceiver(), transfer.getAmount())){
            // Perform the transfer
            transfer.getSender().setBalance(transfer.getSender().getBalance() - transfer.getAmount());
            transfer.getReceiver().setBalance(transfer.getReceiver().getBalance() + transfer.getAmount());

            // Set transfer state to completed
            transfer.setState("COMPLETED");

            // Record the transfer
            recordTransfer(transfer);

            // Update accounts
            accountService.updateAccount(transfer.getSender());
            accountService.updateAccount(transfer.getReceiver());

            // Send email notifications
            sendTransferNotifications(transfer);
        } else {
            // Set transfer state to rejected if validation fails
            transfer.setState("REJECTED");
            recordTransfer(transfer);
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
        if(verify.getType().equals("CURRENT") || verify.getStatus().equals("ACTIVE")){
            return false;
        }
        return true;
    }

    //To verify that sender is not receiver
    public boolean senderNotReceiver(Account sender, Account receiver, double amount){
        if(sender.getId() == receiver.getId() || receiver.getStatus().equals("BLOCK")){
            return false;
        }
        return verifyBalance(sender, amount);
    }

    public void recordTransfer(Transfer transfer){
        transferRepo.save(transfer);
    }

    // Method to send email notifications to both sender and receiver
    private void sendTransferNotifications(Transfer transfer) {
        try {
            // Send email to sender
            sendSenderNotification(transfer);

            // Send email to receiver
            sendReceiverNotification(transfer);
        } catch (Exception e) {
            // Log the error but don't fail the transfer
            System.err.println("Failed to send email notifications: " + e.getMessage());
        }
    }

    // Send notification to the sender
    private void sendSenderNotification(Transfer transfer) {
        SimpleMailMessage message = new SimpleMailMessage();

        // Get sender's email from the associated customer
        String senderEmail = userRepo.findById(transfer.getSender().getUserId()).getEmail();

        message.setTo(senderEmail);
        message.setSubject("Transfer Confirmation - Money Sent");

        String messageBody = String.format(
                "Dear %s %s,\n\n" +
                        "Your transfer has been successfully completed.\n\n" +
                        "Transfer Details:\n" +
                        "Amount: $%.2f\n" +
                        "To Account: %s\n" +
                        "Date: %s\n" +
                        "Reference: %d\n" +
                        "New Balance: $%.2f\n\n" +
                        "Thank you for using our banking services.\n\n" +
                        "Best regards,\n" +
                        "St. Jean University Banking System",
                userRepo.findById(transfer.getSender().getUserId()).getFirstName(),
                userRepo.findById(transfer.getSender().getUserId()).getLastName(),
                transfer.getAmount(),
                transfer.getReceiver().getId(),
                transfer.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                transfer.getTransaction_ID(),
                transfer.getSender().getBalance()
        );

        message.setText(messageBody);
        mailSender.send(message);
    }

    // Send notification to the receiver
    private void sendReceiverNotification(Transfer transfer) {
        SimpleMailMessage message = new SimpleMailMessage();

        // Get receiver's email from the associated customer
        String receiverEmail = userRepo.findById(transfer.getReceiver().getUserId()).getEmail();

        message.setTo(receiverEmail);
        message.setSubject("Transfer Notification - Money Received");

        String messageBody = String.format(
                "Dear %s %s,\n\n" +
                        "You have received a transfer to your account.\n\n" +
                        "Transfer Details:\n" +
                        "Amount: $%.2f\n" +
                        "From Account: %s\n" +
                        "Date: %s\n" +
                        "Reference: %d\n" +
                        "New Balance: $%.2f\n\n" +
                        "If you have any questions about this transfer, please contact our customer service.\n\n" +
                        "Best regards,\n" +
                        "St. Jean University Banking System",
                userRepo.findById(transfer.getReceiver().getUserId()).getFirstName(),
                userRepo.findById(transfer.getReceiver().getUserId()).getLastName(),
                transfer.getAmount(),
                transfer.getSender().getId(),
                transfer.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                transfer.getTransaction_ID(),
                transfer.getReceiver().getBalance()
        );

        message.setText(messageBody);
        mailSender.send(message);
    }
}