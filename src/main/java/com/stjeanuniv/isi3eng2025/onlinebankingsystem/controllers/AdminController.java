package com.stjeanuniv.isi3eng2025.onlinebankingsystem.controllers;

import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.AdminDto;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto.AdminRequestDto;
import com.stjeanuniv.isi3eng2025.onlinebankingsystem.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Validated
public class AdminController {

    private final AdminService adminService;

    /**
     * Create a new admin
     */
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody AdminRequestDto adminRequestDto) {
        try {
            log.info("Creating new admin with username: {}", adminRequestDto.getUsername());
            AdminDto createdAdmin = adminService.createAdmin(adminRequestDto);
            return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error("Error creating admin: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Update an existing admin
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<?> updateAdmin(@PathVariable Long id,
                                         @Valid @RequestBody AdminRequestDto adminRequestDto) {
        try {
            log.info("Updating admin with ID: {}", id);
            AdminDto updatedAdmin = adminService.updateAdmin(id, adminRequestDto);
            return ResponseEntity.ok(updatedAdmin);
        } catch (RuntimeException e) {
            log.error("Error updating admin with ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get admin by ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getAdminById(@PathVariable Long id) {
        try {
            AdminDto admin = adminService.getAdminById(id);
            return ResponseEntity.ok(admin);
        } catch (RuntimeException e) {
            log.error("Error fetching admin with ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get admin by username
     */
    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getAdminByUsername(@PathVariable @NotBlank String username) {
        try {
            AdminDto admin = adminService.getAdminByUsername(username);
            return ResponseEntity.ok(admin);
        } catch (RuntimeException e) {
            log.error("Error fetching admin with username {}: {}", username, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get admin by email
     */
    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getAdminByEmail(@PathVariable @Email String email) {
        try {
            AdminDto admin = adminService.getAdminByEmail(email);
            return ResponseEntity.ok(admin);
        } catch (RuntimeException e) {
            log.error("Error fetching admin with email {}: {}", email, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all admins
     */
    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<AdminDto>> getAllAdmins() {
        log.info("Fetching all admins");
        List<AdminDto> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    /**
     * Get admins by department
     */
    @GetMapping("/department/{department}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<AdminDto>> getAdminsByDepartment(@PathVariable String department) {
        log.info("Fetching admins by department: {}", department);
        List<AdminDto> admins = adminService.getAdminsByDepartment(department);
        return ResponseEntity.ok(admins);
    }

    /**
     * Get admins by position
     */
    @GetMapping("/position/{position}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<AdminDto>> getAdminsByPosition(@PathVariable String position) {
        log.info("Fetching admins by position: {}", position);
        List<AdminDto> admins = adminService.getAdminsByPosition(position);
        return ResponseEntity.ok(admins);
    }

    /**
     * Get all active admins
     */
    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<AdminDto>> getActiveAdmins() {
        log.info("Fetching all active admins");
        List<AdminDto> admins = adminService.getActiveAdmins();
        return ResponseEntity.ok(admins);
    }

    /**
     * Get active admins by department
     */
    @GetMapping("/active/department/{department}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<AdminDto>> getActiveAdminsByDepartment(@PathVariable String department) {
        log.info("Fetching active admins by department: {}", department);
        List<AdminDto> admins = adminService.getActiveAdminsByDepartment(department);
        return ResponseEntity.ok(admins);
    }

    /**
     * Get active admins by position
     */
    @GetMapping("/active/position/{position}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<AdminDto>> getActiveAdminsByPosition(@PathVariable String position) {
        log.info("Fetching active admins by position: {}", position);
        List<AdminDto> admins = adminService.getActiveAdminsByPosition(position);
        return ResponseEntity.ok(admins);
    }

    /**
     * Delete admin permanently
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        try {
            log.info("Deleting admin with ID: {}", id);
            adminService.deleteAdmin(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error deleting admin with ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deactivate admin account
     */
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deactivateAdmin(@PathVariable Long id) {
        try {
            log.info("Deactivating admin with ID: {}", id);
            adminService.deactivateAdmin(id);
            return ResponseEntity.ok(Map.of("message", "Admin deactivated successfully"));
        } catch (RuntimeException e) {
            log.error("Error deactivating admin with ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Activate admin account
     */
    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> activateAdmin(@PathVariable Long id) {
        try {
            log.info("Activating admin with ID: {}", id);
            adminService.activateAdmin(id);
            return ResponseEntity.ok(Map.of("message", "Admin activated successfully"));
        } catch (RuntimeException e) {
            log.error("Error activating admin with ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}