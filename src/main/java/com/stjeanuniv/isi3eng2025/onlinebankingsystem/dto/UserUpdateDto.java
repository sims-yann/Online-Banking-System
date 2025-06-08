package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String fullName;
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^\\+?[0-9\\-\\s]+$", message = "Phone number should be valid")
    @Size(min = 6, max = 20, message = "Phone number must be between 6 and 20 characters")
    private String phone;
}
