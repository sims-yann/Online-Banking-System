package com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@DiscriminatorValue(
        value = "Transfer"
)
@EqualsAndHashCode(callSuper = true)
public class Transfer extends Transaction{

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String state;

    public @NotNull Account getSender() {
        return sender;
    }

    public void setSender(@NotNull Account sender) {
        this.sender = sender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public @NotNull Account getReceiver() {
        return receiver;
    }

    public void setReceiver(@NotNull Account receiver) {
        this.receiver = receiver;
    }

    public @NotNull LocalDateTime getTime() {
        return time;
    }

    public void setTime(@NotNull LocalDateTime time) {
        this.time = time;
    }

    @NotNull
    @Min(value = 0)
    public double getAmount() {
        return amount;
    }

    public void setAmount(@NotNull @Min(value = 0) double amount) {
        this.amount = amount;
    }


    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Account sender;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Account receiver;

    @NotNull
    private LocalDateTime time;

    @NotNull
    @Min(value = 0)
    private double amount;


}
