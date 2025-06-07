package com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue(
        value = "Customer"
)
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {
    public String FirstName;
    public String LastName;
    public String Address;
    public Date BirthDate;

    public Customer(String name, String password, String email, double phone, Date creationDate, String firstName, String lastName, String address, Date birthDate) {
        super(name, password, email, phone, creationDate);
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Address = address;
        this.BirthDate = birthDate;
    }
//   @Column(unique = true, nullable = true, columnDefinition = "VARCHAR(255) DEFAULT 'UNKNOWN'")
//    private String NationalCardNumber;

}
