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

    public SecuritySettingsDto(int sessionTimeout, boolean requireStrongPassword, boolean enableTwoFactorAuth) {
        this.sessionTimeout = sessionTimeout;
        this.requireStrongPassword = requireStrongPassword;
        this.enableTwoFactorAuth = enableTwoFactorAuth;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public boolean isRequireStrongPassword() {
        return requireStrongPassword;
    }

    public void setRequireStrongPassword(boolean requireStrongPassword) {
        this.requireStrongPassword = requireStrongPassword;
    }

    public boolean isEnableTwoFactorAuth() {
        return enableTwoFactorAuth;
    }

    public void setEnableTwoFactorAuth(boolean enableTwoFactorAuth) {
        this.enableTwoFactorAuth = enableTwoFactorAuth;
    }
}
