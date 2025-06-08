package com.stjeanuniv.isi3eng2025.onlinebankingsystem.serviceimpl;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.UserDTO;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.UserUpdateDto;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.AccountStatus;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Role;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.User;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.exception.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories.*;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User registerAdmin(UserDTO userDto) {
        if (emailExists(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhone(userDto.getPhone());
        user.setRole(Role.ADMIN);
        user.setActive(AccountStatus.ACTIVE);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User registerCustomer(UserDTO userDto) {
        if (emailExists(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhone(userDto.getPhone());
        user.setRole(Role.CUSTOMER);
        user.setActive(AccountStatus.ACTIVE);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long userId, UserUpdateDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (userDto.getFullName() != null) {
            user.setFullName(userDto.getFullName());
        }
        if (userDto.getPhone() != null) {
            user.setPhone(userDto.getPhone());
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void changeUserStatus(Long userId, AccountStatus status) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.updateUserStatus(userId, status);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
       return userRepository.findByRole(role);
    }

    @Override
    public List<User> getRecentUsers() {
        return userRepository.findByCreatedAtBetween(LocalDateTime.now().minusDays(30), LocalDateTime.now());
    }

    @Override
    public long countAllUsers() {
        return userRepository.count();
    }

    @Override
    public long countActiveUsers() {
        return userRepository.countActiveUsers();
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
