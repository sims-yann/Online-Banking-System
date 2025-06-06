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
public class Customer extends User {
    public String FirstName;
    public String LastName;
    public String Address;
    public Date BirthDate;
    @Column(unique = true, nullable = false)
    private String NationalCardNumber;



    public Customer() {
        this.role = "CUSTOMER";
    }
}
