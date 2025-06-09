package com.stjeanuniv.isi3eng2025.onlinebankingsystem.services;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerAdmin(UserDTO userDto);
    User registerCustomer(UserDTO userDto);
    User updateUser(Long userId, UserUpdateDto userDto);
    void changeUserStatus(Long userId, AccountStatus status);
    Optional<User> getUserById(Long userId);
    User getUserByEmail(String email);
    Page<User> getAllUsers(Pageable pageable);
    List<User> getUsersByRole(Role role);
    List<User> getRecentUsers();
    long countAllUsers();
    long countActiveUsers();
    boolean emailExists(String email);
}