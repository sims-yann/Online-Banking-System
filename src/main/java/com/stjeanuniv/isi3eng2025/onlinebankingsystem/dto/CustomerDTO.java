package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CustomerDTO {

    private Long userId;
    private String username;
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

}


