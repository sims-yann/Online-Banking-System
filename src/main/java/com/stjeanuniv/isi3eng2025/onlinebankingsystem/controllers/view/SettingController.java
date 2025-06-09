package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.view;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.SystemSetting;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.SystemSettingRepo;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/admin/settings")
public class SettingController {

    @Autowired
    private SystemSettingRepo settingsRepository;

    @GetMapping("/security")
    public String securitySettings(Model model) {
        model.addAttribute("sessionTimeout",
                settingsRepository.findBySettingKey("session.timeout").orElse(new SystemSetting("session.timeout", "30")));
        model.addAttribute("strongPasswords",
                settingsRepository.findBySettingKey("security.strong_passwords").orElse(new SystemSetting("security.strong_passwords", "true")));
        model.addAttribute("twoFactorAuth",
                settingsRepository.findBySettingKey("security.two_factor").orElse(new SystemSetting("security.two_factor", "false")));
        return "admin/admin-settings";
    }

    @PostMapping("/security")
    public String saveSecuritySettings(@RequestParam Map<String, String> allParams) {
        allParams.forEach((key, value) -> {
            if (key.startsWith("security.") || key.equals("session.timeout")) {
                SystemSetting setting = settingsRepository.findBySettingKey(key)
                        .orElse(new SystemSetting(key, value));
                setting.setSettingValue(value);
                settingsRepository.save(setting);
            }
        });
        return "redirect:/admin/settings/security?success";
    }

    @GetMapping("/transactions")
    public String transactionSettings(Model model) {
        model.addAttribute("dailyLimit",
                settingsRepository.findBySettingKey("transaction.daily_limit").orElse(new SystemSetting("transaction.daily_limit", "10000")));
        model.addAttribute("perTransactionLimit",
                settingsRepository.findBySettingKey("transaction.per_transaction_limit").orElse(new SystemSetting("transaction.per_transaction_limit", "5000")));
        model.addAttribute("approvalThreshold",
                settingsRepository.findBySettingKey("transaction.approval_threshold").orElse(new SystemSetting("transaction.approval_threshold", "1000")));
        return "admin/admin-settings";
    }
}