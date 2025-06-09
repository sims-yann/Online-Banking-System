package com.stjeanuniv.isi3eng2025.onlinebankingsystem;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Account;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Role;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.User;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.UserRepo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class Conf {
    @Autowired
    private UserRepo user_repo;

    @Setter
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner commandLineRunner(UserRepo user_repo) {
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
            }

            Account acct1 = new Account();

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
