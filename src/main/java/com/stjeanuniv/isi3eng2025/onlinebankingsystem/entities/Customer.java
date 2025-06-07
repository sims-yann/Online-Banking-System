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
    @Column(unique = true)
    private String NationalCardNumber;

    public Customer() {
        this.role = "CUSTOMER";
    }

    public String getNationalCardNumber() {
        return NationalCardNumber;
    }

    public void setNationalCardNumber(String nationalCardNumber) {
        NationalCardNumber = nationalCardNumber;
    }

    public Date getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(Date birthDate) {
        BirthDate = birthDate;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }
}
