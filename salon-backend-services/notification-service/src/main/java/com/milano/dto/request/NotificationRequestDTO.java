package com.milano.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDTO {
    private String type;
    private String description;
    private UUID userId;
    private UUID bookingId;
    private UUID salonId;
}