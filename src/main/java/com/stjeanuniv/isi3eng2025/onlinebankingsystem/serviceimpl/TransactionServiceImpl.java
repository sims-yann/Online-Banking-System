
package com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.impl;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.TransactionRequestDTO;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.TransactionResponseDTO;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Override
    public TransactionResponseDTO processDeposit(String username, TransactionRequestDTO requestDTO) {
        // Dummy implementation
        return new TransactionResponseDTO("DEPOSIT", requestDTO.getAmount(), "Success");
    }

    @Override
    public TransactionResponseDTO processWithdrawal(String username, TransactionRequestDTO requestDTO) {
        // Dummy implementation
        return new TransactionResponseDTO("WITHDRAWAL", requestDTO.getAmount(), "Success");
    }
}
