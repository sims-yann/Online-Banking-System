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
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User {
    public String Department;
    public String Position;

    public Admin(String name, String password, String email, int phone, Date creationdate, String department, String position) {
        super(name, password, email, phone, creationdate);
        this.Department = department;
        this.Position = position;
    }

}
