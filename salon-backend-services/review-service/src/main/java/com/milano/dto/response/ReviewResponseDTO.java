package com.milano.dto.response;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDTO {
    private UUID id;
    private String reviewText;
    private double rating;
    private UUID salonId;
    private UUID userId;
    private LocalDateTime createdAt;
}
