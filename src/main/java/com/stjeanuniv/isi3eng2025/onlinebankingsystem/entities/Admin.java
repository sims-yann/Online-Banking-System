package com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@DiscriminatorValue(
        value = "Admin"
)
public class Admin extends User {
    @Column(unique = true, nullable = false)
    private String EmployeeID;
    public String Department;
    public String Position;
}
