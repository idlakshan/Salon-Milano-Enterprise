package com.milano.dto.response;

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
public class OfferingResponseDTO {

    private UUID id;

    private String name;

    private String description;

    private double price;

    private int duration;

    private UUID salonId;

    private boolean available;

    private UUID categoryId;

    private String image;
}
