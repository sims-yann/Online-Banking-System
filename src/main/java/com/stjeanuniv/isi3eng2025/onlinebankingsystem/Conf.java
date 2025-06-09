package com.stjeanuniv.isi3eng2025.onlinebankingsystem;

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
//                Admin admin = new Admin(
//                    "Admin",
//                    passwordEncoder.encode("0000"),  // Encode the password with BCrypt
//                    "admin123@email.com",
//                    658180273,
//                    new java.text.SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01"),
//                    "IT",
//                    "System Administrator"
//                );
                user_repo.save(user);
                System.out.println("Admin user created successfully");
            }


            // Migrate any existing plain text passwords to BCrypt
            List<User> users = user_repo.findAll();
            int updatedCount = 0;

            for (User user : users) {
                if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
                    String plainPassword = user.getPassword();
                    String encodedPassword = passwordEncoder.encode(plainPassword);
                    user.setPassword(encodedPassword);
                    user_repo.save(user);
                    updatedCount++;
                    System.out.println("Updated password for user: " + user.getFullName());
                }
            }
            System.out.println("Password migration complete. Updated " + updatedCount + " users.");
        };
    }
}
