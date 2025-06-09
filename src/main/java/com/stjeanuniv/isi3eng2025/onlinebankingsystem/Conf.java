package com.stjeanuniv.isi3eng2025.onlinebankingsystem;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.AccountRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.UserRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl.AccountServiceImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class Conf {
    @Autowired
    private UserRepo user_repo;

    @Setter
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner commandLineRunner(UserRepo user_repo, AccountServiceImpl acctservice, AccountRepo accountRepo) {
        return args -> {
            // Create admin user if it doesn't exist
            if (user_repo.count() == 0) {
                User user = new User();
                user.setFullName("Admin");
                user.setPassword(passwordEncoder.encode("0000"));
                user.setEmail("admin123@email.com");
                user.setPhone("658180273");
                user.setRole(com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Role.ADMIN);
                user.setActive(com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.AccountStatus.ACTIVE);
                user.setCreatedAt(java.time.LocalDateTime.now());
                user.setUpdatedAt(java.time.LocalDateTime.now());
                user_repo.save(user);
                System.out.println("User created");

                User user1 = new User();
                user1.setFullName("User1");
                user1.setPassword(passwordEncoder.encode("0000"));
                user1.setEmail("user1@email.com");
                user1.setPhone("658180273");
                user1.setRole(Role.CUSTOMER);
                user1.setCreatedAt(java.time.LocalDateTime.now());
                user1.setUpdatedAt(java.time.LocalDateTime.now());
                user1.setActive(com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.AccountStatus.ACTIVE);
                user_repo.save(user1);
                System.out.println("customer user created successfully");

                Account acct1 = new Account();
                acct1.setAccountNumber(acctservice.generateAccountNumber());
                acct1.setAccountType(com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.AccountType.SAVINGS);
                acct1.setBalance(BigDecimal.valueOf(200000.00));
                acct1.setUser(user1);
                acct1.setStatus(AccountStatus.ACTIVE);
                acct1.setCreatedAt(java.time.LocalDateTime.now());
                acct1.setUpdatedAt(java.time.LocalDateTime.now());
                accountRepo.save(acct1);
                System.out.println("acct number: "+acctservice.generateAccountNumber());

                Account acct2 = new Account();
                acct2.setAccountNumber(acctservice.generateAccountNumber());
                acct2.setAccountType(AccountType.CHECKING);
                acct2.setBalance(BigDecimal.valueOf(500000.00));
                acct2.setUser(user1);
                acct2.setStatus(AccountStatus.ACTIVE);
                acct2.setCreatedAt(java.time.LocalDateTime.now());
                acct2.setUpdatedAt(java.time.LocalDateTime.now());
                accountRepo.save(acct1);
                System.out.println("acct number: "+acctservice.generateAccountNumber());
            }



            // Migrate any existing plain text passwords to BCrypt
//            List<User> users = user_repo.findAll();
//            int updatedCount = 0;
//
//            for (User user : users) {
//                if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
//                    String plainPassword = user.getPassword();
//                    String encodedPassword = passwordEncoder.encode(plainPassword);
//                    user.setPassword(encodedPassword);
//                    user_repo.save(user);
//                    updatedCount++;
//                    System.out.println("Updated password for user: " + user.getFullName());
//                }
//            }
//            System.out.println("Password migration complete. Updated " + updatedCount + " users.");
        };
    }
}
