package com.milano.dto;

import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalonDTO {
    private UUID id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String city;
    private boolean isOpen;
    private boolean homeService;
    private boolean active;
    private UUID ownerId;
    private LocalTime openTime;
    private LocalTime closeTime;
    private List<String> images;
}
