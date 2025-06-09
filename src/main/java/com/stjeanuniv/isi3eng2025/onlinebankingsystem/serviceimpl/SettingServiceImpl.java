package com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.SecuritySettingsDto;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.TransactionSettingsDto;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.SystemSetting;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.SystemSettingRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SystemSettingRepo settingRepository;

    @Override
    public Map<String, String> getAllSecuritySettings() {
     List<SystemSetting> settings = settingRepository.findAllSecuritySettings();
        return settings.stream()
                .collect(Collectors.toMap(SystemSetting::getSettingKey, SystemSetting::getSettingValue));
    }

    @Override
    public Map<String, String> getAllTransactionSettings() {
        List<SystemSetting> settings = settingRepository.findAllTransactionSettings();
        return settings.stream().collect(Collectors.toMap(SystemSetting::getSettingKey, SystemSetting::getSettingValue));
    }

    @Override
    public Map<String, String> getAllNotificationSettings() {
        List<SystemSetting> settings = settingRepository.findAllNotificationSettings();
        return settings.stream().collect(Collectors.toMap(SystemSetting::getSettingKey, SystemSetting::getSettingValue));
    }

    @Override
    @Transactional
    public void updateSecuritySettings(SecuritySettingsDto settings) {
      updateSetting("security.session_timeout", String.valueOf(settings.getSessionTimeout()));
      updateSetting("security.strong_passwords", String.valueOf(settings.isRequireStrongPassword()));
      updateSetting("security.two_factor", String.valueOf(settings.isEnableTwoFactorAuth()));
    }

    @Override
    @Transactional
    public void updateTransactionSettings(TransactionSettingsDto settings) {
        updateSetting("transaction.daily_limit", settings.getDailyTransferLimit().toString());
        updateSetting("transaction.per_transaction_limit", settings.getPerTransactionLimit().toString());
        updateSetting("transaction.approval_threshold", settings.getRequireApprovalAbove().toString());

    }

    @Override
    public void updateNotificationSettings(Map<String, String> settings) {
        settings.forEach(this::updateSetting);
    }

    @Override
    public SystemSetting getSettingByKey(String key) {
        return settingRepository.findBySettingKey(key).orElseGet(() ->{SystemSetting setting = new SystemSetting();
        setting.setSettingKey(key);
        setting.setSettingValue("");
        return setting;});
    }

    @Override
    public String getSettingValue(String key, String defaultValue) {
        return settingRepository.findBySettingKey(key)
                .map(SystemSetting::getSettingValue)
                .orElse(defaultValue);
    }

    @Override
    public int getSessionTimeout() {
        return Integer.parseInt(getSettingValue("security,session_timeout","30"));
    }

    @Override
    public BigDecimal getDailyTransferLimit() {
        return new BigDecimal(getSettingValue("security,daily_limit","10000"));
    }

    @Override
    public BigDecimal getPerTransactionLimit() {
        return new BigDecimal(getSettingValue("security,per_transaction_limit","10000"));
    }

    @Override
    public BigDecimal getApprovalThreshold() {
        return new BigDecimal(getSettingValue("security,approval_threshold","1000"));
    }
    private void updateSetting(String key, String value) {
        SystemSetting setting = settingRepository.findBySettingKey(key).orElseGet(()-> new SystemSetting(key,value));
        setting.setSettingValue(value);
        settingRepository.save(setting);
    }
}