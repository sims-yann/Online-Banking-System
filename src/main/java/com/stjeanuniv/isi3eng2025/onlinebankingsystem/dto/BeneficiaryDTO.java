package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import java.time.LocalDateTime;

public class BeneficiaryDTO {
    private Long beneficiaryId;
    private String name;
    private String accountNumber;
    private String bankName;
    private String bankCode;
    private String relationship;
    private String accountType;
    private boolean isTrusted;
    private LocalDateTime dateAdded;
    private Long userId;

}

