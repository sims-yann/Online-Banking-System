package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.AccountStatus;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.AccountType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private String accountNumber;

    private BigDecimal balance;

    private AccountType type;

    private LocalDateTime createdAt;

    private AccountStatus status;

}
