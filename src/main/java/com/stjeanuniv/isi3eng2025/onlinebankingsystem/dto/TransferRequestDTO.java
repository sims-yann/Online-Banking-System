package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferRequestDTO {
    private Long fromAccountId;
    private Long toAccountId; 
    private BigDecimal amount;
    private String description;
    private String transactionPin;
    private String otpCode;
    private boolean isFutureDate;
    private LocalDate scheduledDate;
    private boolean isRecurring;
    private String recurringFrequency; 

}
