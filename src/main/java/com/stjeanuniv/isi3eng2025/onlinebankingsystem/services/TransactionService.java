package com.stjeanuniv.isi3eng2025.onlinebankingsystem.services;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.TransactionRequestDTO;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.TransactionResponseDTO;

public interface TransactionService {

    TransactionResponseDTO processDeposit(String username, TransactionRequestDTO request);

    TransactionResponseDTO processWithdrawal(String username, TransactionRequestDTO request);
}

