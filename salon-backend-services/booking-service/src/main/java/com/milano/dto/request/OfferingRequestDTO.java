package com.milano.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferingRequestDTO {

    private UUID id;

    private String name;

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
