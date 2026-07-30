package com.milano.dto.response;

import com.milano.dto.BookingDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDTO {

    private UUID id;
    private String type;
    private String description;
    private boolean read;
    private UUID userId;
    private UUID bookingId;
    private UUID salonId;
    private LocalDateTime createdAt;
    private BookingDTO booking;
}