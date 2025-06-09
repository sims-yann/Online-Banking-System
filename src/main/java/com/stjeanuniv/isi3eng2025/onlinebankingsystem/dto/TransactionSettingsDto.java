package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSettingsDto {
    private BigDecimal dailyTransferLimit;
    private BigDecimal perTransactionLimit;
    private BigDecimal requireApprovalAbove;

    public void setDailyTransferLimit(BigDecimal dailyTransferLimit) {
        this.dailyTransferLimit = dailyTransferLimit;
    }

    public void setPerTransactionLimit(BigDecimal perTransactionLimit) {
        this.perTransactionLimit = perTransactionLimit;
    }

    public void setRequireApprovalAbove(BigDecimal requireApprovalAbove) {
        this.requireApprovalAbove = requireApprovalAbove;
    }

    public BigDecimal getDailyTransferLimit() {
        return dailyTransferLimit;
    }

    public BigDecimal getPerTransactionLimit() {
        return perTransactionLimit;
    }

    public BigDecimal getRequireApprovalAbove() {
        return requireApprovalAbove;
    }
}
