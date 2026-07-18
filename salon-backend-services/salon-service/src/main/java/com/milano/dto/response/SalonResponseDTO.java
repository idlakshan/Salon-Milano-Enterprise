package com.milano.dto.response;

import lombok.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SalonResponseDTO {

    private UUID id;
    private String name;
    private List<String> images;
    private String address;
    private String phoneNumber;
    private String email;
    private String city;
    private boolean open;
    private boolean homeService;
    private boolean active;
    private UUID ownerId;
    private LocalTime openTime;
    private LocalTime closeTime;

}