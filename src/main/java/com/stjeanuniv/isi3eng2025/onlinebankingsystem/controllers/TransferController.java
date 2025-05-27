package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Transfer;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    /**
     * Record a new transfer
     */
    @PostMapping
    public ResponseEntity<String> recordTransfer(@RequestBody Transfer transfer) {
        try {
            transferService.recordTransfer(transfer);
            return ResponseEntity.ok("Transfer recorded successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error recording transfer: " + e.getMessage());
        }
    }

    /**
     * Apply a transfer (process the transaction)
     */
    @PostMapping("/apply")
    public ResponseEntity<String> applyTransfer(@RequestBody Transfer transfer) {
        try {
            if (!transferService.verifyAccountType(transfer.getSender()) ||
                    !transferService.senderNotReceiver(transfer.getSender(), transfer.getReceiver(), transfer.getAmount()) ||
                    !transferService.verifyBalance(transfer.getSender(), transfer.getAmount())) {
                return ResponseEntity.badRequest().body("Transfer validation failed.");
            }

            transferService.applyTransfer(transfer);
            return ResponseEntity.ok("Transfer applied successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error applying transfer: " + e.getMessage());
        }
    }

    /**
     * Get transfer details as a map (custom format)
     */
    @PostMapping("/details")
    public ResponseEntity<?> getTransferDetails(@RequestBody Transfer transfer) {
        try {
            Map<String, Object> details = transferService.showTransferDetails(transfer);
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving transfer details: " + e.getMessage());
        }
    }

    /**
     * Get list of all transfers
     */
    @GetMapping
    public ResponseEntity<?> getAllTransfers() {
        try {
            List<Transfer> transfers = transferService.getTransferList();
            return ResponseEntity.ok(transfers);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching transfers: " + e.getMessage());
        }
    }
}

