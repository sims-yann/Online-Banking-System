package com.stjeanuniv.isi3eng2025.onlinebankingsystem.services;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface SettingService {
    Map<String, String> getAllSecuritySettings();
    Map<String, String> getAllTransactionSettings();
    Map<String, String> getAllNotificationSettings();
    void updateSecuritySettings(SecuritySettingsDto settings);
    void updateTransactionSettings(TransactionSettingsDto settings);
    void updateNotificationSettings(Map<String, String> settings);
    SystemSetting getSettingByKey(String key);
    String getSettingValue(String key, String defaultValue);
    int getSessionTimeout();
    BigDecimal getDailyTransferLimit();
    BigDecimal getPerTransactionLimit();
    BigDecimal getApprovalThreshold();
}
