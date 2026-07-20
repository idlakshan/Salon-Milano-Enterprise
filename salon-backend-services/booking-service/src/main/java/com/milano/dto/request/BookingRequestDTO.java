package com.milano.dto.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDTO {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Set<UUID> servicesIds;

}
