package com.milano.dto.request;


import com.milano.dto.BookingDTO;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDTO {
    private String type;
    private String description;
    private UUID userId;
    private UUID bookingId;
    private UUID salonId;
    private BookingDTO booking;
}
