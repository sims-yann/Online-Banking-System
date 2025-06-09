package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.view;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.config.CustomeruserDetails;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transaction;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.User;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.TransactionRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.UserRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl.AccountServiceImpl;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private final UserRepo userRepo;

    @Autowired
    private final TransactionRepo transactionRepo;

    @Autowired
    private final AccountServiceImpl accountService;

    @Autowired
    private final TransactionServiceImpl transactionService;

    public CustomerController(UserRepo userRepo, TransactionRepo transactionRepo, AccountServiceImpl accountService, TransactionServiceImpl transactionService) {
        this.userRepo = userRepo;
        this.transactionRepo = transactionRepo;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/dashboard")
   public String Dashboard(Model model, Principal principal) {
        Authentication authentication = (Authentication) principal;
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername(); // Assuming CustomUserDetails has an email field
        User user = userRepo.findByEmail(email);
        List<Transaction> user_transactions =new ArrayList<Transaction>();
        BigDecimal total_balance = BigDecimal.valueOf(0);
        List<Account> useraccounts = accountService.getUserAccounts(user.getId());
        for (Account account : useraccounts) {
            user_transactions.addAll(transactionService.getTransactionsByAccountId(account.getId()));
            total_balance = total_balance.add(account.getBalance());
        }

        model.addAttribute("email", email);
        model.addAttribute("username", user.getFullName());
        model.addAttribute("total_balance", total_balance);
        model.addAttribute("useraccounts", useraccounts);
        model.addAttribute("user_transactions", user_transactions);
        return "/Customer/user-dashboard";
    }

    @GetMapping("/profile")
    public String Profile(Model model) {
        return "/Customer/profile";
    }

    @GetMapping("/transactions")
    public String Transactions(Model model) {
        return "/Customer/transactions";
    }

    @GetMapping("/accounts")
    public String Accounts(Model model) {
        return "/Customer/accounts";
    }

    @GetMapping("/transfer")
    public String Transfer(Model model) {
        return "/Customer/transfer";
    }

    @GetMapping("/deposit-Withdraw")
    public String DepositWithdraw(Model model) {
        return "/Customer/deposit-withdraw";
    }
}
