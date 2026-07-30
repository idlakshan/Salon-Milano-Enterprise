package com.milano.api;

import com.milano.dto.request.NotificationRequestDTO;
import com.milano.dto.response.NotificationResponseDTO;
import com.milano.service.NotificationService;
import com.milano.util.StandardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<StandardResponseDTO> createNotification(@RequestBody NotificationRequestDTO requestDTO) {

        NotificationResponseDTO response = notificationService.createNotification(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(StandardResponseDTO.builder()
                        .code(201)
                        .message("Notification created successfully")
                        .data(response)
                        .build()
                );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<StandardResponseDTO> getByUser(@PathVariable UUID userId) {
        List<NotificationResponseDTO> responseDTOList = notificationService.getAllNotificationsByUserId(userId);
        return ResponseEntity.ok(
                StandardResponseDTO.builder()
                        .code(200)
                        .message("Notifications retrieved")
                        .data(responseDTOList)
                        .build());
    }


    @GetMapping("/salon/{salonId}")
    public ResponseEntity<StandardResponseDTO> getBySalon(@PathVariable UUID salonId) {
        List<NotificationResponseDTO> notificationsBySalonId = notificationService.getAllNotificationsBySalonId(salonId);
        return ResponseEntity.ok(
                StandardResponseDTO.builder()
                        .code(200)
                        .message("Notifications retrieved")
                        .data(notificationsBySalonId)
                        .build()
        );
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<StandardResponseDTO> markRead(@PathVariable UUID id) {
        NotificationResponseDTO notificationResponseDTO = notificationService.markNotificationAsRead(id);
        return ResponseEntity.ok(
                StandardResponseDTO.builder()
                        .code(200)
                        .message("Notification marked as read")
                        .data(notificationResponseDTO)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> delete(@PathVariable UUID id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(
                StandardResponseDTO.builder()
                        .code(200)
                        .message("Notification deleted successfully")
                        .data(null)
                        .build()
        );
    }
}