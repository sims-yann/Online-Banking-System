package com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "TYPE",
        discriminatorType = DiscriminatorType.STRING
)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Transaction_ID;

    @NotNull
    @Min(value = 0)
    private double amount;

    private String description;

    private Date date;

    private String status;

    @ManyToOne
    @JoinColumn(name = "Source_Account", unique = false, nullable = true, updatable = false)
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "Destination_Account", unique = false, nullable = true, updatable = false)
    private Account destinationAccount;

    @NotNull
    private LocalDateTime time;

    public int getTransaction_ID() {
        return Transaction_ID;
    }

    public void setTransaction_ID(int transaction_ID) {
        Transaction_ID = transaction_ID;
    }

    @NotNull
    @Min(value = 0)
    public double getAmount() {
        return amount;
    }

    public void setAmount(@NotNull @Min(value = 0) double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public @NotNull LocalDateTime getTime() {
        return time;
    }

    public void setTime(@NotNull LocalDateTime time) {
        this.time = time;
    }
}
