package com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Entity
@DiscriminatorValue(
        value = "Transfer"
)
@EqualsAndHashCode(callSuper = true)
public class Transfer extends Transaction{

}
