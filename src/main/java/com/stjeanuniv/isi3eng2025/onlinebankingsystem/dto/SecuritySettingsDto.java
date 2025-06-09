package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecuritySettingsDto {
    private int sessionTimeout;
    private boolean requireStrongPassword;
    private boolean enableTwoFactorAuth;
}
