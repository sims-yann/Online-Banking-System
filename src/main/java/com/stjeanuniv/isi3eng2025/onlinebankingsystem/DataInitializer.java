package com.stjeanuniv.isi3eng2025.onlinebankingsystem;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.AccountRepo;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepo userRepo;
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepo userRepo,
                           AccountRepo accountRepo,
                           PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Only initialize if no users exist
        if (userRepo.count() == 0) {
            createAdmin("admin1", "admin1@bank.com", "IT", "Manager", "A1001");
            createAdmin("admin2", "admin2@bank.com", "HR", "Director", "A1002");
            createCustomer("customer1", "customer1@bank.com", "John", "Doe",
                    "123 Main St", "1990-01-01", "ID-123456", "C1001");
            createCustomer("customer2", "customer2@bank.com", "Jane", "Smith",
                    "456 Oak Ave", "1992-05-15", "ID-654321", "C1002");
        }
    }

    private void createAdmin(String username, String email,
                             String department, String position,
                             String accountNumber) {
        Admin admin = new Admin();
        admin.setName(username);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(username + "Pass")); // admin1Pass
        admin.setPhone(1234567890);
        admin.setCreationDate(new Date());
        admin.setDepartment(department);
        admin.setPosition(position);
        userRepo.save(admin);

        createAccount(admin, accountNumber, "Admin Account", 50000.0);
    }

    private void createCustomer(String username, String email,
                                String firstName, String lastName,
                                String address, String birthDateStr,
                                String nationalId, String accountNumber) {
        Customer customer = new Customer();
        customer.setName(username);
        customer.setEmail(email);
        customer.setPassword(passwordEncoder.encode(username + "Pass")); // customer1Pass
        customer.setPhone(9876543210L);
        customer.setCreationDate(new Date());
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddress(address);
        customer.setNationalCardNumber(nationalId);
        // Parse birth date - implement date parsing logic if needed
        userRepo.save(customer);

        createAccount(customer, accountNumber, "Savings", 2500.0);
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