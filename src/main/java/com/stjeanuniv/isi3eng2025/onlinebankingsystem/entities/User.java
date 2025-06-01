package com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "USERTYPE",
        discriminatorType = DiscriminatorType.STRING
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public String name;
    public String password;
    public String email;
    public double phone;
    public Date CreationDate;
    public Date Lastlogin;
    
    @Column(nullable = false)
    public String role; // e.g., 'ADMIN' or 'CUSTOMER'
}
