package com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue(
        value = "Admin"
)
public class Admin extends User {
    public String Department;
    public String Position;

    public Admin() {
        this.role = "ADMIN";
    }
}
