package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByFullName(String username);
    List<User> findAll();

    List<User> findByRole(Role role);

    @Query("SELECT COUNT(u) FROM User u WHERE u.active = true")
    long countActiveUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.active = false")
    long countInactiveUsers();

    @Query("SELECT u FROM User u WHERE u.fullName LIKE %:keyword% OR u.email LIKE %:keyword%")
    List<User> searchUsers(String keyword);

    @Query("SELECT u FROM User u ORDER BY u.createdAt DESC LIMIT 5")
    List<User> findRecentUsers();
}
