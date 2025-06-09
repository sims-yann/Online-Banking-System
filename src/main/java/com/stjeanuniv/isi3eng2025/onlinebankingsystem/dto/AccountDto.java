package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.AccountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class AccountDto {
    @NotNull(message = "required")
    private Long userId;

    @NotNull(message = "required")
    private AccountType accountType;

    @Size(min = 10, max = 20, message = "between 10 and 20")
    private String accountNumber;

    @DecimalMin(value = "0.00", message = "initial balance can't be negative")
    //@Digits(integer = 10, fraction = 2)
    private BigDecimal balance= BigDecimal.ZERO;
}
