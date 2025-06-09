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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
