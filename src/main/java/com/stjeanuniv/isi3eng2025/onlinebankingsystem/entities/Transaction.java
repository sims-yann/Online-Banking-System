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
    private Account SourceAccount;

    @ManyToOne
    @JoinColumn(name = "Destination_Account", unique = false, nullable = true, updatable = false)
    private Account DestinationAccount;

    @NotNull
    private LocalDateTime time;

}
