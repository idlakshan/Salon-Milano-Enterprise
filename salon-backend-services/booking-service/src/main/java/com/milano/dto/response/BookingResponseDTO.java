package com.milano.dto.response;

import com.milano.entity.BOOKING_STATUS;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDTO {

    private UUID id;
    private UUID salonId;
    private UUID customerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Set<UUID> serviceId;
    private BOOKING_STATUS status;
    private double totalPrice;
}
