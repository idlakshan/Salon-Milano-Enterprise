package com.milano.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private UUID id;
    private String type;
    private Boolean isRead= false;
    private String description;
    private UUID userId;
    private UUID bookingId;
    private UUID salonId;
    private BookingDTO booking;
}
