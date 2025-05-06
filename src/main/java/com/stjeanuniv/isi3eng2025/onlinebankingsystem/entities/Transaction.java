package com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities;

import jakarta.persistence.*;
import lombok.*;

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

    private double amount;
    private String description;
    private Date date;
    public String type;
    private String status;
    @ManyToOne
    @JoinColumn(name = "Source_Account", unique = false, nullable = true, updatable = false)
    private Customer SourceAccount;
    @ManyToOne
    @JoinColumn(name = "Destination_Account", unique = false, nullable = true, updatable = false)
    private Customer DestinationAccount;

}
