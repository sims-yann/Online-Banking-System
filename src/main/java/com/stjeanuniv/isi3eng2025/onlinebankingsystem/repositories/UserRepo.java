package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findByFullName(String username);
    List<User> findAll();
    Optional<User> findById(Long id);

    List<User> findByRole(Role role);
    List<User> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT COUNT(u) FROM User u WHERE u.active = com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.AccountStatus.ACTIVE")
    long countActiveUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.active = com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.AccountStatus.SUSPENDED")
    long countInactiveUsers();


    @Query("SELECT u FROM User u WHERE u.fullName LIKE %:keyword% OR u.email LIKE %:keyword%")
    List<User> searchUsers(String keyword);

    @Query("SELECT u FROM User u ORDER BY u.createdAt DESC LIMIT 5")
    List<User> findRecentUsers();

    @Modifying
    @Query("UPDATE User u set u.active= :status WHERE u.id= :userId")
    void updateUserStatus(Long userId, AccountStatus status);

    boolean existsByEmail(String email);

    List<User> findTop5ByOrderByCreatedAtDesc();
}
