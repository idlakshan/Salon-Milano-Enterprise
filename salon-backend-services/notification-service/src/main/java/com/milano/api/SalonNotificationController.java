package com.milano.api;

import com.milano.dto.response.NotificationResponseDTO;
import com.milano.service.NotificationService;
import com.milano.util.StandardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications/salon-owner")
@RequiredArgsConstructor
public class SalonNotificationController {

    private final NotificationService notificationService;


    @GetMapping("/salon/{salonId}")
    public ResponseEntity<StandardResponseDTO> getNotificationsBySalonId(@PathVariable UUID salonId) {

        List<NotificationResponseDTO> notifications = notificationService.getAllNotificationsBySalonId(salonId);

        return ResponseEntity.ok(
                StandardResponseDTO.builder()
                        .code(200)
                        .message("Salon notifications retrieved successfully")
                        .data(notifications)
                        .build()
        );
    }
}
