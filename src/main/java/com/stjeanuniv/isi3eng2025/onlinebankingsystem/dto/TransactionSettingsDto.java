package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSettingsDto {
    private BigDecimal dailyTransferLimit;
    private BigDecimal perTransactionLimit;
    private BigDecimal requireApprovalAbove;
}
