package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {


    private int userId;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
    private boolean isActive;
    private LocalDateTime lastLoginTime;
    private String securityQuestion;
    private CustomerPreferencesDTO preferences;


    public class CustomerPreferencesDTO {
        private boolean emailNotificationsEnabled;
        private boolean smsNotificationsEnabled;
        private boolean twoFactorAuthEnabled;
        private String language;
        private String timeZone;

    }

    public class CustomerLoginRequestDTO {
        private String username;
        private String password;
        private String deviceInfo;
        private String ipAddress;

    }

    public class CustomerLoginResponseDTO {
        private String accessToken;
        private String refreshToken;
        private LocalDateTime tokenExpiration;
        private CustomerDTO userDetails;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public CustomerPreferencesDTO getPreferences() {
        return preferences;
    }

    public void setPreferences(CustomerPreferencesDTO preferences) {
        this.preferences = preferences;
    }
}


