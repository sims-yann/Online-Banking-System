package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers.endpoint_controllers;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.CustomerDTO;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Customer;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customerDTO", new CustomerDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute("customerDTO") CustomerDTO customerDTO, Model model) {
        if (customerRepo.findByName(customerDTO.getUsername()) != null) {
            model.addAttribute("usernameExists", true);
            return "register";
        }
        Customer customer = new Customer();
        customer.setName(customerDTO.getUsername());
        customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        customer.setEmail(customerDTO.getEmail());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setPhone(Double.parseDouble(customerDTO.getPhoneNumber()));
        customer.setAddress(customerDTO.getAddress());
        customer.setBirthDate(java.sql.Date.valueOf(customerDTO.getDateOfBirth()));
        // Set other fields as needed
        customerRepo.save(customer);
        model.addAttribute("success", true);
        return "register";
    }
} 