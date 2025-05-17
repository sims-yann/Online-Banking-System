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
public class Transfer extends Transaction{

    @Getter
    @Setter
    private String state;

    @NotNull
    @ManyToOne
    private Account sender;

    @NotNull
    @ManyToOne
    private Account receiver;

    @NotNull
    private LocalDateTime time;

    @NotNull
    @Min(value = 0)
    private double amount;
}
