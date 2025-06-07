package com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "USERTYPE",
        discriminatorType = DiscriminatorType.STRING
)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public String name;
    public String password;
    public String email;
    public double phone;
    public Date CreationDate;

    public User(String name, String password, String email, double phone, Date CreationDate) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.CreationDate = CreationDate;
    }
}
