package com.stjeanuniv.isi3eng2025.onlinebankingsystem.repositories;


import com.stjeanuniv.isi3eng2025.onlinebankingsystem.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * Find admin by username
     */
    Optional<Admin> findByUsername(String username);

    /**
     * Find admin by email
     */
    Optional<Admin> findByEmail(String email);

    /**
     * Find all admins by department
     */
    List<Admin> findByDepartment(String department);

    /**
     * Find all admins by position
     */
    List<Admin> findByPosition(String position);

    /**
     * Find admins by both department and position
     */
    List<Admin> findByDepartmentAndPosition(String department, String position);

    /**
     * Find all active admins
     */
    @Query("SELECT a FROM Admin a WHERE a.active = true")
    List<Admin> findActiveAdmins();

    /**
     * Find active admins by department
     */
    @Query("SELECT a FROM Admin a WHERE a.department = :department AND a.active = true")
    List<Admin> findActiveAdminsByDepartment(@Param("department") String department);

    /**
     * Find active admins by position
     */
    @Query("SELECT a FROM Admin a WHERE a.position = :position AND a.active = true")
    List<Admin> findActiveAdminsByPosition(@Param("position") String position);

    /**
     * Check if username exists
     */
    boolean existsByUsername(String username);

    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);

    /**
     * Count admins by department
     */
    @Query("SELECT COUNT(a) FROM Admin a WHERE a.department = :department")
    long countByDepartment(@Param("department") String department);

    /**
     * Count active admins
     */
    @Query("SELECT COUNT(a) FROM Admin a WHERE a.active = true")
    long countActiveAdmins();
}
