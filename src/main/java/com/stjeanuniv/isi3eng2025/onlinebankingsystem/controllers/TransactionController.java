package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.TransactionRequestDTO;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.TransactionResponseDTO;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for handling transaction operations like deposits and withdrawals
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deposit(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Validated TransactionRequestDTO requestDTO) {
        try {
            TransactionResponseDTO responseDTO = transactionService.processDeposit(
                    userDetails.getUsername(), requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your deposit: " + e.getMessage());
        }
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> withdraw(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Validated TransactionRequestDTO requestDTO) {
        try {
            TransactionResponseDTO responseDTO = transactionService.processWithdrawal(
                    userDetails.getUsername(), requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your withdrawal: " + e.getMessage());
        }
    }
}
