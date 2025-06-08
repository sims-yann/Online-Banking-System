package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.AccountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
