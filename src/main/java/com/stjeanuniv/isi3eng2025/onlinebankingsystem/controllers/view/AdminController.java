package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.view;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.AccountService;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.SettingService;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.TransactionService;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepo transactionRepository;

    @Autowired
    private AccountRepo accountRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long totalUsers = userRepository.count();
        long activeUsers = userRepository.countActiveUsers();
        long totalAccounts = accountRepository.count();
        long activeAccounts = accountRepository.countActiveAccounts();
        long totalTransactions = transactionRepository.count();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("activeUsers", activeUsers);
        model.addAttribute("totalAccounts", totalAccounts);
        model.addAttribute("activeAccounts", activeAccounts);
        model.addAttribute("totalTransactions", totalTransactions);

        // Add recent users and transactions
        model.addAttribute("recentUsers", userRepository.findTop5ByOrderByCreatedAtDesc());
        model.addAttribute("recentTransactions", transactionRepository.findTop5ByOrderByCreatedAtDesc());

        return "/admin/admin-dashboard";
    }

    @GetMapping("/users")
    public String userManagement(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("roles", Role.values());
        model.addAttribute("statuses", AccountStatus.values());
        return "/admin/admin-users";
    }

    @GetMapping("/users/{id}")
    public String getUserDetails(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("userUpdateDto", new UserUpdateDto());
        model.addAttribute("roles", Role.values());
        model.addAttribute("statuses", AccountStatus.values());
        return "/admin/admin-users";
    }

    @PostMapping("/users/create")
    public String createUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO, 
                             BindingResult result, 
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/admin/admin-users";
        }

        try {
            if (userDTO.getRole() == Role.ADMIN) {
                userService.registerAdmin(userDTO);
            } else {
                userService.registerCustomer(userDTO);
            }
            redirectAttributes.addFlashAttribute("successMessage", "User created successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating user: " + e.getMessage());
        }

        return "redirect:/admin/users";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable Long id, 
                             @Valid @ModelAttribute("userUpdateDto") UserUpdateDto userUpdateDto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/admin/admin-users";
        }

        try {
            userService.updateUser(id, userUpdateDto);
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating user: " + e.getMessage());
        }

        return "redirect:/admin/users";
    }

    @PostMapping("/users/status/{id}")
    public String changeUserStatus(@PathVariable Long id, 
                                  @RequestParam AccountStatus status,
                                  RedirectAttributes redirectAttributes) {
        try {
            userService.changeUserStatus(id, status);
            redirectAttributes.addFlashAttribute("successMessage", 
                "User status changed to " + status.toString());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error changing user status: " + e.getMessage());
        }

        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            // Soft delete by changing status to SUSPENDED
            userService.changeUserStatus(id, AccountStatus.SUSPENDED);
            redirectAttributes.addFlashAttribute("successMessage", "User deactivated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deactivating user: " + e.getMessage());
        }

        return "redirect:/admin/users";
    }

    @GetMapping("/transactions")
    public String transactionManagement(Model model) {
        model.addAttribute("transactions", transactionRepository.findAll());
        model.addAttribute("pendingTransactions", transactionRepository.findByTransactionStatus(TransactionStatus.PENDING));
        return "/admin/admin-transaction";
    }

    @PostMapping("/transactions/approve/{id}")
    public String approveTransaction(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            transactionService.approveTransaction(id);
            redirectAttributes.addFlashAttribute("successMessage", "Transaction approved successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error approving transaction: " + e.getMessage());
        }
        return "redirect:/admin/transactions";
    }

    @PostMapping("/transactions/reject/{id}")
    public String rejectTransaction(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            transactionService.rejectTransaction(id);
            redirectAttributes.addFlashAttribute("successMessage", "Transaction rejected successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error rejecting transaction: " + e.getMessage());
        }
        return "redirect:/admin/transactions";
    }

    @GetMapping("/settings")
    public String systemSettings(Model model) {
        // Load transaction settings
        Map<String, String> transactionSettings = settingService.getAllTransactionSettings();

        // Create DTO with default values in case settings don't exist
        TransactionSettingsDto transactionSettingsDto = new TransactionSettingsDto(
            new BigDecimal(transactionSettings.getOrDefault("transaction.daily_limit", "10000")),
            new BigDecimal(transactionSettings.getOrDefault("transaction.per_transaction_limit", "5000")),
            new BigDecimal(transactionSettings.getOrDefault("transaction.approval_threshold", "1000"))
        );

        model.addAttribute("transactionSettings", transactionSettingsDto);
        return "/admin/admin-settings";
    }

    @PostMapping("/settings/transaction")
    public String updateTransactionSettings(@ModelAttribute TransactionSettingsDto transactionSettingsDto,
                                           RedirectAttributes redirectAttributes) {
        try {
            settingService.updateTransactionSettings(transactionSettingsDto);
            redirectAttributes.addFlashAttribute("successMessage", "Transaction settings updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating transaction settings: " + e.getMessage());
        }
        return "redirect:/admin/settings";
    }

    @GetMapping("/accounts")
    public String adminAccountsPage(Model model) {
        List<Account> allAccounts = accountService.getAllAccounts();
        List<Account> pendingAccounts = allAccounts.stream()
                .filter(account -> account.getStatus() == AccountStatus.INACTIVE)
                .toList();

        model.addAttribute("pendingAccounts", pendingAccounts);
        model.addAttribute("allAccounts", allAccounts);

        return "Admin/accounts";
    }

    @PostMapping("/accounts/{accountId}/approve")
    @ResponseBody
    public ResponseEntity<?> approveAccount(@PathVariable Long accountId) {
        try {
            accountService.changeAccountStatus(accountId, AccountStatus.ACTIVE);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Account approved successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Failed to approve account: " + e.getMessage()
            ));
        }
    }

    @PostMapping("/accounts/{accountId}/reject")
    @ResponseBody
    public ResponseEntity<?> rejectAccount(@PathVariable Long accountId) {
        try {
            accountService.changeAccountStatus(accountId, AccountStatus.CLOSED);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Account rejected successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Failed to reject account: " + e.getMessage()
            ));
        }
    }

    @PostMapping("/accounts/{accountId}/suspend")
    @ResponseBody
    public ResponseEntity<?> suspendAccount(@PathVariable Long accountId) {
        try {
            accountService.changeAccountStatus(accountId, AccountStatus.SUSPENDED);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Account suspended successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Failed to suspend account: " + e.getMessage()
            ));
        }
    }


}
