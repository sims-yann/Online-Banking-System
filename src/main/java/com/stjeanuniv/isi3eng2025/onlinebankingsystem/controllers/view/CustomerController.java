package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.view;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.*;
import org.springframework.ui.*;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private AccountRepo accountRepository;

    @Autowired
    private TransactionRepo transactionRepository;

    @Autowired
    private UserRepo userRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Account> accounts = accountRepository.findByUserId(user.getId());
        BigDecimal totalBalance = accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDate weekAgo = LocalDate.now().minusDays(7);
        List<Transaction> recentTransactions = transactionRepository
                .findByFromAccountIdOrToAccountId(user.getId(), user.getId());

        model.addAttribute("user", user);
        model.addAttribute("accounts", accounts);
        model.addAttribute("totalBalance", totalBalance);
        model.addAttribute("recentTransactions", recentTransactions);

        return "/Customer/user-dashboard";
    }
}