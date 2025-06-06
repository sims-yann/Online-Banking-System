package com.stjeanuniv.isi3eng2025.onlinebankingsystem.services;


import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.AdminDto;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.AdminRequestDto;

import java.util.List;

/**
 * Service interface for Admin management operations
 */
public interface AdminService {

    /**
     * Create a new admin
     * @param adminRequestDto Admin data
     * @return Created admin DTO
     * @throws RuntimeException if username or email already exists
     */
    AdminDto createAdmin(AdminRequestDto adminRequestDto);

    /**
     * Update an existing admin
     * @param id Admin ID
     * @param adminRequestDto Updated admin data
     * @return Updated admin DTO
     * @throws RuntimeException if admin not found or username/email conflict
     */
    AdminDto updateAdmin(Long id, AdminRequestDto adminRequestDto);

    /**
     * Get admin by ID
     * @param id Admin ID
     * @return Admin DTO
     * @throws RuntimeException if admin not found
     */
    AdminDto getAdminById(Long id);

    /**
     * Get admin by username
     * @param username Admin username
     * @return Admin DTO
     * @throws RuntimeException if admin not found
     */
    AdminDto getAdminByUsername(String username);

    /**
     * Get admin by email
     * @param email Admin email
     * @return Admin DTO
     * @throws RuntimeException if admin not found
     */
    AdminDto getAdminByEmail(String email);

    /**
     * Get all admins
     * @return List of admin DTOs
     */
    List<AdminDto> getAllAdmins();

    /**
     * Get admins by department
     * @param department Department name
     * @return List of admin DTOs
     */
    List<AdminDto> getAdminsByDepartment(String department);

    /**
     * Get admins by position
     * @param position Position name
     * @return List of admin DTOs
     */
    List<AdminDto> getAdminsByPosition(String position);

    /**
     * Get all active admins
     * @return List of active admin DTOs
     */
    List<AdminDto> getActiveAdmins();

    /**
     * Get active admins by department
     * @param department Department name
     * @return List of active admin DTOs in the department
     */
    List<AdminDto> getActiveAdminsByDepartment(String department);

    /**
     * Get active admins by position
     * @param position Position name
     * @return List of active admin DTOs with the position
     */
    List<AdminDto> getActiveAdminsByPosition(String position);

    /**
     * Delete admin permanently
     * @param id Admin ID
     * @throws RuntimeException if admin not found
     */
    void deleteAdmin(Long id);

    /**
     * Deactivate admin account
     * @param id Admin ID
     * @throws RuntimeException if admin not found
     */
    void deactivateAdmin(Long id);

    /**
     * Activate admin account
     * @param id Admin ID
     * @throws RuntimeException if admin not found
     */
    void activateAdmin(Long id);

    /**
     * Change admin password
     * @param id Admin ID
     * @param oldPassword Current password
     * @param newPassword New password
     * @return true if password changed successfully, false if old password is incorrect
     * @throws RuntimeException if admin not found
     */
    boolean changePassword(Long id, String oldPassword, String newPassword);

    /**
     * Count total admins
     * @return Total number of admins
     */
    long countTotalAdmins();

    /**
     * Count active admins
     * @return Number of active admins
     */
    long countActiveAdmins();

    /**
     * Count admins by department
     * @param department Department name
     * @return Number of admins in the department
     */
    long countAdminsByDepartment(String department);
}