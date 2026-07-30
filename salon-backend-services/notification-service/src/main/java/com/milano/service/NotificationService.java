package com.milano.service;

import com.milano.dto.request.NotificationRequestDTO;
import com.milano.dto.response.NotificationResponseDTO;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

    NotificationResponseDTO createNotification(NotificationRequestDTO notification);
    List<NotificationResponseDTO> getAllNotificationsByUserId(UUID userId);
    List<NotificationResponseDTO> getAllNotificationsBySalonId(UUID salonId);
    NotificationResponseDTO markNotificationAsRead(UUID notificationId);
    void deleteNotification(UUID notificationId);
    List<NotificationResponseDTO> getAllNotifications();

}
