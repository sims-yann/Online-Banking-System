package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.view;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private TransactionRepo transactionRepository;

    @Autowired
    private AccountRepo accountRepository;

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
        return "/admin/admin-users";
    }

    @GetMapping("/transactions")
    public String transactionManagement(Model model) {
        model.addAttribute("transactions", transactionRepository.findAll());
        return "/admin/admin-transaction";
    }

    @GetMapping("/settings")
    public String systemSettings(Model model) {
        // Load settings from database or default values
        return "/admin/admin-settings";
    }
}