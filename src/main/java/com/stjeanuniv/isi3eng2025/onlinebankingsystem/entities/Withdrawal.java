package com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@DiscriminatorValue(
        value = "Withdrawal"
)
public class Withdrawal extends Transaction {

}
