package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.view;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.AccountDto;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.TransactionHistoryDTO;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.AccountService;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.*;
import org.springframework.ui.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

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

    @GetMapping("/profile")
    public String Profile(Model model) {
        return "/Customer/profile";
    }

    @GetMapping("/transactions")
    public String transactionsPage(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Account> accounts = accountRepository.findByUserId(user.getId());

        // Get recent transactions (last 30 days by default)
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        TransactionHistoryDTO criteria = new TransactionHistoryDTO();
        criteria.setStartDate(startDate);

        List<Transaction> transactions = transactionService.filterTransactions(criteria, user.getId());

        model.addAttribute("user", user);
        model.addAttribute("accounts", accounts);
        model.addAttribute("transactions", transactions);
        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("transactionStatuses", TransactionStatus.values());
        model.addAttribute("searchCriteria", criteria);

        return "/Customer/transactions";
    }

    @PostMapping("/transactions/search")
    public String searchTransactions(
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) TransactionType transactionType,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) TransactionStatus status,
            Model model, Principal principal) {

        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Account> accounts = accountRepository.findByUserId(user.getId());

        // Create search criteria
        TransactionHistoryDTO criteria = new TransactionHistoryDTO();
        criteria.setAccountNumber(accountNumber);
        criteria.setTransactionType(transactionType);
        criteria.setDescription(description);
        criteria.setStatus(status);

        // Convert LocalDate to LocalDateTime for start and end dates
        if (startDate != null) {
            criteria.setStartDate(LocalDateTime.of(startDate, LocalTime.MIN));
        }

        if (endDate != null) {
            criteria.setEndDate(LocalDateTime.of(endDate, LocalTime.MAX));
        }

        // Get filtered transactions
        List<Transaction> transactions = transactionService.filterTransactions(criteria, user.getId());

        model.addAttribute("user", user);
        model.addAttribute("accounts", accounts);
        model.addAttribute("transactions", transactions);
        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("transactionStatuses", TransactionStatus.values());
        model.addAttribute("searchCriteria", criteria);

        return "/Customer/transactions";
    }

    @GetMapping("/transactions/{id}")
    @ResponseBody
    public Transaction getTransactionDetails(@PathVariable Long id, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = transactionService.getTransactionById(id);

        // Verify that the transaction belongs to the user
        boolean isUserTransaction = false;
        if (transaction.getFromAccount() != null && transaction.getFromAccount().getUser().getId().equals(user.getId())) {
            isUserTransaction = true;
        }
        if (transaction.getToAccount() != null && transaction.getToAccount().getUser().getId().equals(user.getId())) {
            isUserTransaction = true;
        }

        if (!isUserTransaction) {
            throw new RuntimeException("Unauthorized access to transaction");
        }

        return transaction;
    }

    @GetMapping("/accounts")
    public String accountsPage(Model model, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Account> accounts = accountService.getUserAccounts(user.getId());
        BigDecimal totalBalance = accountService.getTotalBalanceByUser(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("accounts", accounts);
        model.addAttribute("totalBalance", totalBalance);
        model.addAttribute("accountTypes", AccountType.values());

        return "Customer/accounts";
    }

    @PostMapping("/accounts/create")
    @ResponseBody
    public ResponseEntity<?> createAccount(@RequestBody AccountDto accountDto, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        accountDto.setUserId(user.getId());

        Account newAccount = accountService.createAccount(accountDto);

        Map<String, Object> response = Map.of(
            "success", true,
            "message", "Account created successfully. It will be active after admin approval.",
            "accountId", newAccount.getId(),
            "accountNumber", newAccount.getAccountNumber(),
            "accountType", newAccount.getAccountType(),
            "status", newAccount.getStatus()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/accounts/{id}")
    @ResponseBody
    public ResponseEntity<?> getAccountDetails(@PathVariable Long id, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = accountService.getAccountById(id);

        // Verify that the account belongs to the user
        if (!account.getUser().getId().equals(user.getId())) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Unauthorized access to account"
            ));
        }

        Map<String, Object> accountDetails = accountService.viewAccountDetails(id);

        return ResponseEntity.ok(Map.of(
            "success", true,
            "account", accountDetails
        ));
    }
}
