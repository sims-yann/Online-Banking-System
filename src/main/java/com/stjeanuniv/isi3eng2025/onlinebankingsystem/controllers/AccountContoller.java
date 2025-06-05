package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountContoller {

    private final AccountService accountService;

    @Autowired
    public AccountContoller(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Create a new account
     */
    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        try {
            accountService.CreateAccount(account);
            return ResponseEntity.ok("Account created successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating account: " + e.getMessage());
        }
    }

    /**
     * Update an existing account
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable int id, @RequestBody Account account) {
        try {
            Account existing = accountService.getAccount(id);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }

            account.setId(id); // ensure the ID is set
            accountService.updateAccount(account);
            return ResponseEntity.ok("Account updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating account: " + e.getMessage());
        }
    }

    /**
     * Delete an account
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable int id) {
        try {
            Account account = accountService.getAccount(id);
            if (account == null) {
                return ResponseEntity.notFound().build();
            }

            accountService.DeleteAccount(account);
            return ResponseEntity.ok("Account deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting account: " + e.getMessage());
        }
    }

    /**
     * Get an account by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable int id) {
        try {
            Account account = accountService.getAccount(id);
            if (account == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving account: " + e.getMessage());
        }
    }
}
