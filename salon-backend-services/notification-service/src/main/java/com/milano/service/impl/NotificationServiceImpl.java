package com.milano.service.impl;

import com.milano.dto.request.NotificationRequestDTO;
import com.milano.dto.response.NotificationResponseDTO;
import com.milano.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO notification) {
        return null;
    }

    @Override
    public List<NotificationResponseDTO> getAllNotificationsByUserId(UUID userId) {
        return List.of();
    }

    @Override
    public List<NotificationResponseDTO> getAllNotificationsBySalonId(UUID salonId) {
        return List.of();
    }

    @Override
    public NotificationResponseDTO markNotificationAsRead(UUID notificationId) {
        return null;
    }

    @Override
    public void deleteNotification(UUID notificationId) {

    }

    @Override
    public List<NotificationResponseDTO> getAllNotifications() {
        return List.of();
    }
}
