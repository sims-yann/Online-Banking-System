package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SystemSettingRepo extends JpaRepository<SystemSetting, Long> {
    Optional<SystemSetting> findBySettingKey(String settingKey);

    @Query("SELECT s FROM SystemSetting s WHERE s.settingKey LIKE 'security.%'")
    List<SystemSetting> findAllSecuritySettings();

    @Query("SELECT s FROM SystemSetting s WHERE s.settingKey LIKE 'transaction.%'")
    List<SystemSetting> findAllTransactionSettings();

    @Query("SELECT s FROM SystemSetting s WHERE s.settingKey LIKE 'notification.%'")
    List<SystemSetting> findAllNotificationSettings();

}
