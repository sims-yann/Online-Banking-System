package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Long transactionId;
    private String transactionType;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime timestamp;
    private String description;
    private String reference;
    private String status;
    private Long fromAccountId;
    private Long toAccountId;
    private String paymentMethod;
    private String category;
    private BigDecimal balanceAfterTransaction;
    private String transactionLocation;

}

