package com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = false)
    private User user;

    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> outgoingTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> incomingTransactions = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
