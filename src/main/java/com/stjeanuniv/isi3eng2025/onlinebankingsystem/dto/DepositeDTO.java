package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.TransactionStatus;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositeDTO {

    private List<Account> from_account;

    private String to_account;

    private String recipient_name;

    private BigDecimal amount;

    private String description;

    private TransactionType type;

    private TransactionStatus status;

}
