package com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.TransactionDTO;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.exception.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepo accountRepository;

    @Autowired
    private TransactionRepo transactionRepository;

    @Autowired
    private SettingService settingService;

    @Override
    @Transactional
    public Transaction transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description) throws Exception {
        // Validate accounts
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new Exception("Source account not found"));
        
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new Exception("Destination account not found"));

        // Validate amount
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Transfer amount must be greater than zero");
        }

        // Check sufficient balance
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new Exception("Insufficient balance in source account");
        }

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setTransactionType("TRANSFER");
        transaction.setStatus("PENDING");
        transaction.setDescription(description);

        try {
            // Update account balances
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));

            // Save updated accounts
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            // Update transaction status and save
            transaction.setStatus("COMPLETED");
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            transaction.setStatus("FAILED");
            transactionRepository.save(transaction);
            throw new Exception("Transfer failed: " + e.getMessage());
        }
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(java.time.LocalDateTime start, java.time.LocalDateTime end) {
        return transactionRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(start, end);
    }

    @Override
    public List<Transaction> getRecentTransactions(int count) {
        count=30;
        return transactionRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime.now().minusDays(count), LocalDateTime.now());
    }

    @Override
    public List<Transaction> getUserTransactions(Long userId, LocalDateTime since) {
        return transactionRepository.findUserTransactionSince(userId,since);
    }

    @Override
    public long countAllTransactions() {
        return transactionRepository.count();
    }

    @Override
    public BigDecimal getDailyTransactionTotal(Long userId) {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return transactionRepository.getTotalTransfersBetween(startOfDay, LocalDateTime.now());
    }

    @Override
    public boolean exceedsDailyLimit(Long userId, BigDecimal amount) {
        BigDecimal dailyTransactionTotal = getDailyTransactionTotal(userId);
        BigDecimal dailyLimit = settingService.getDailyTransferLimit();
        return dailyTransactionTotal.add(amount).compareTo(dailyLimit) >0;
    }

    @Override
    public Transaction createTransaction(TransactionDTO transactionDto) {
        Account fromAccount = null;
        Account toAccount = null;

        if (transactionDto.getFromAccountId() != null) {
            fromAccount = accountRepository.findById(transactionDto.getFromAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("From account not found"));
        }

        if (transactionDto.getToAccountId() != null) {
            toAccount = accountRepository.findById(transactionDto.getToAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("To account not found"));
        }

        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(transactionDto.getAmount());
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setDescription(transactionDto.getDescription());

        BigDecimal approvalThreshold = settingService.getApprovalThreshold();
        if (transactionDto.getAmount().compareTo(approvalThreshold) > 0) {
            transaction.setStatus(TransactionStatus.PENDING);
        } else {
            processTransaction(transaction);
        }

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public Transaction approveTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (transaction.getTransactionStatus() != TransactionStatus.PENDING) {
            throw new InvalidTransactionException("Only pending transactions can be approved");
        }

        processTransaction(transaction);
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public Transaction rejectTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (transaction.getTransactionStatus() != TransactionStatus.PENDING) {
            throw new InvalidTransactionException("Only pending transactions can be rejected");
        }

        transaction.setTransactionStatus(TransactionStatus.FAILED);
        return transactionRepository.save(transaction);
    }

    private void processTransaction(Transaction transaction) {
        try {
            switch (transaction.getTransactionType()) {
                case DEPOSIT:
                    processDeposit(transaction);
                    break;
                case WITHDRAW:
                    processWithdrawal(transaction);
                    break;
                case TRANSFER:
                    processTransfer(transaction);
                    break;
                default:
                    throw new InvalidTransactionException("Invalid transaction type");
            }
            transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        } catch (Exception e) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            throw e;
        }
    }

    private void processDeposit(Transaction transaction) {
        Account toAccount = transaction.getToAccount();
        accountRepository.updateAccountBalance(toAccount.getId(), transaction.getAmount());
    }

    private void processWithdrawal(Transaction transaction) {
        Account fromAccount = transaction.getFromAccount();
        if (fromAccount.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal");
        }
        accountRepository.updateAccountBalance(fromAccount.getId(), transaction.getAmount().negate());
    }

    private void processTransfer(Transaction transaction) {
        Account fromAccount = transaction.getFromAccount();
        Account toAccount = transaction.getToAccount();

        if (fromAccount.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for transfer");
        }

        accountRepository.updateAccountBalance(fromAccount.getId(), transaction.getAmount().negate());
        accountRepository.updateAccountBalance(toAccount.getId(), transaction.getAmount());
    }
} 