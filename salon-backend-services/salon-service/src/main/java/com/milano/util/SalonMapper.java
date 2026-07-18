package com.milano.util;

import com.milano.dto.request.SalonRequestDTO;
import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.SalonResponseDTO;
import com.milano.entity.Salon;
import com.milano.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class SalonMapper {

    public Salon toSalon(SalonRequestDTO salonRequestDTO, UserRequestDTO userRequestDTO) {
        if (salonRequestDTO == null || userRequestDTO == null) throw new ValidationException("DTO Not Found");

        return Salon.builder().name(salonRequestDTO.getName())
                .images(salonRequestDTO.getImages())
                .address(salonRequestDTO.getAddress())
                .phoneNumber(salonRequestDTO.getPhoneNumber())
                .email(salonRequestDTO.getEmail())
                .city(salonRequestDTO.getCity())
                .isOpen(true)
                .homeService(true)
                .active(true)
                .ownerId(userRequestDTO.getId())
                .openTime(salonRequestDTO.getOpenTime())
                .closeTime(salonRequestDTO.getCloseTime()).build();
    }

    public SalonResponseDTO toSalonResponseDTO(Salon salon) {
        if (salon == null) throw new ValidationException("Customer Entity Not Found");
        return SalonResponseDTO.builder()
                .id(salon.getId())
                .name(salon.getName())
                .images(salon.getImages())
                .address(salon.getAddress())
                .phoneNumber(salon.getPhoneNumber())
                .email(salon.getEmail())
                .city(salon.getCity())
                .open(salon.isOpen())
                .homeService(salon.isHomeService())
                .active(salon.isActive())
                .ownerId(salon.getOwnerId())
                .openTime(salon.getOpenTime())
                .closeTime(salon.getCloseTime())
                .build();
    }
}
