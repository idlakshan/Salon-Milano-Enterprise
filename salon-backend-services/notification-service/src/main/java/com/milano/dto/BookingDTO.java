package com.milano.dto;

import com.milano.entity.BOOKING_STATUS;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDTO {

    private UUID id;
    private UUID salonId;
    private UUID customerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Set<UUID> serviceId;
    private BOOKING_STATUS status;
    private double totalPrice;
}
