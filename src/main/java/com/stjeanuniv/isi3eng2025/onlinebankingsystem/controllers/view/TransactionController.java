package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.view;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.AccountDto;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transfer")
    public String showTransferForm(Model model) {



        return "transaction/transfer";
    }

    @PostMapping("/transfer")
    public String processTransfer(
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String description,
            RedirectAttributes redirectAttributes) {

        try {
            Transaction transaction = transactionService.transferMoney(
                fromAccountNumber,
                toAccountNumber,
                amount,
                description
            );

            redirectAttributes.addFlashAttribute("success", "Transfer completed successfully!");
            return "redirect:/transactions/transfer";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/transactions/transfer";
        }
    }

    @GetMapping("/{id}")
    public String viewTransaction(@PathVariable Long id, Model model) {
        try {
            Transaction transaction = transactionService.getTransactionById(id);
            model.addAttribute("transaction", transaction);
            return "transaction/view";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/range")
    @ResponseBody
    public List<Transaction> getTransactionsByDateRange(@RequestParam String start, @RequestParam String end) {
        java.time.LocalDateTime startDate = java.time.LocalDateTime.parse(start);
        java.time.LocalDateTime endDate = java.time.LocalDateTime.parse(end);
        return transactionService.getTransactionsByDateRange(startDate, endDate);
    }
}