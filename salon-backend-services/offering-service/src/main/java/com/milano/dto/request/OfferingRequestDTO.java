package com.milano.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferingRequestDTO {

    @NotBlank(message = "Offering name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Positive(message = "Price must be greater than zero")
    private double price;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    private UUID salonId;

    private boolean available;

    private UUID category;

    private String image;
}
