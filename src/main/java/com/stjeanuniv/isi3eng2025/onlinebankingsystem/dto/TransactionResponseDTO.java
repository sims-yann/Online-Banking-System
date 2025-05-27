package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

public class TransactionResponseDTO {

    private String type;
    private Double amount;
    private String status;

    public TransactionResponseDTO(String type, Double amount, String status) {
        this.type = type;
        this.amount = amount;
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}
