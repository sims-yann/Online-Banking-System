package com.stjeanuniv.isi3eng2025.onlinebankingsystem;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.AccountRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.AdminRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepo userRepo;
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepo userRepo,
                           AccountRepo accountRepo,
                           PasswordEncoder passwordEncoder
                           ) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
    }



    public Date parseDateLegacy(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(dateStr);
    }

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if no users exist
        if (userRepo.count() == 0) {
            createAdmin("admin1", "ismaelulrich121@gmail.com", "IT", "Manager", "A1001");
            createAdmin("admin2", "ismaelulrich121@gmail.com", "HR", "Director", "A1002");
            createCustomer("customer1", "ismaelulrich121@gmail.com", "John", "Doe",
                    "123 Main St", "1990-01-01", "ID-123456", "C1001");
            createCustomer("customer2", "ismaelulrich121@gmail.com", "Jane", "Smith",
                    "456 Oak Ave", "1992-05-15", "ID-654321", "C1002");
        }
    }

    private void createAdmin(String username, String email,
                             String department, String position,
                             String accountNumber) {
        User admin = new User();
        admin.setFullName(username);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(username + "Pass")); // admin1Pass
        admin.setPhone("1234567890");
        admin.setActive(AccountStatus.ACTIVE);
        admin.setRole(Role.ADMIN);
        userRepo.save(admin);


        createAccount(admin, , 50000.0);
    }

    private void createCustomer(String username, String email,
                                String firstName, String lastName,
                                String address, String birthDateStr,
                                String nationalId, String accountNumber) {
        User customer = new User();
        customer.setFullName(username);
        customer.setEmail(email);
        customer.setPassword(passwordEncoder.encode(username + "Pass")); // customer1Pass
        customer.setPhone("9876543210L");

        try {
            customer.setBirthDate(parseDateLegacy(birthDateStr));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        userRepo.save(customer);

        createAccount(customer, accountNumber, "CURRENT", 2500.0);
    }

    private void createAccount(User user, String accountNumber,
                               String type, double balance) {
        Account account = new Account();
        account.setType(type);
        account.setBalance(balance);
        account.setCreatedDate(new Date());
        account.setStatus("Active");
        account.setUserId(user.getId());
        accountRepo.save(account);
    }
}