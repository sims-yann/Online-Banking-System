package com.stjeanuniv.isi3eng2025.onlinebankingsystem.dto;

import java.time.LocalDateTime;

public class NotificationDTO {
    private Long notificationId;
    private String type;
    private String message;
    private LocalDateTime timestamp;
    private boolean isRead;
    private Long userId;
    private String priority;

    // Getters and setters omitted for brevity
}

