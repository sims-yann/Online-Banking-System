package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Role;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.User;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class AuthController {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        @RequestParam(value = "session", required = false) String session,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        if (session != null) {
            model.addAttribute("message", "Your session has expired. Please log in again.");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("customer") User user,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        // Check if username already exists
        if (userRepo.findByFullName(user.getFullName()).isPresent()) {
            result.rejectValue("name", "error.user", "Username already exists");
        }
        
        // Check if passwords match (assuming confirmPassword is passed separately)
        if (result.hasErrors()) {
            return "auth/register";
        }
        
        // Encode password and set creation date
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.CUSTOMER);
        
        // Save the customer
        userRepo.save(user);
        
        redirectAttributes.addFlashAttribute("success", "Registration successful! Please log in.");
        return "redirect:/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }
}