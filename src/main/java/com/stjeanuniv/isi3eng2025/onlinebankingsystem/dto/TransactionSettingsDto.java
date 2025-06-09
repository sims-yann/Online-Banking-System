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

    public BigDecimal getDailyTransferLimit() {
        return dailyTransferLimit;
    }

    public void setDailyTransferLimit(BigDecimal dailyTransferLimit) {
        this.dailyTransferLimit = dailyTransferLimit;
    }

    public BigDecimal getPerTransactionLimit() {
        return perTransactionLimit;
    }

    public void setPerTransactionLimit(BigDecimal perTransactionLimit) {
        this.perTransactionLimit = perTransactionLimit;
    }

    public BigDecimal getRequireApprovalAbove() {
        return requireApprovalAbove;
    }

    public void setRequireApprovalAbove(BigDecimal requireApprovalAbove) {
        this.requireApprovalAbove = requireApprovalAbove;
    }
}
