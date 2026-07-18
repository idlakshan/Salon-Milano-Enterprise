package com.milano.util;

import com.milano.dto.request.CategoryRequestDTO;
import com.milano.dto.request.OfferingRequestDTO;
import com.milano.dto.request.SalonRequestDTO;
import com.milano.dto.response.OfferingResponseDTO;
import com.milano.entity.Offering;
import com.milano.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class OfferingMapper {

    public Offering toOffering(SalonRequestDTO salonRequestDTO,
                               OfferingRequestDTO offeringRequestDTO,
                               CategoryRequestDTO categoryRequestDTO
    ) {
        if (salonRequestDTO == null || offeringRequestDTO == null || categoryRequestDTO == null) {
            throw new ValidationException("Required DTO is missing");
        }

        return Offering.builder()
                .name(offeringRequestDTO.getName())
                .description(offeringRequestDTO.getDescription())
                .price(offeringRequestDTO.getPrice())
                .duration(offeringRequestDTO.getDuration())
                .salonId(salonRequestDTO.getId())
                .categoryId(categoryRequestDTO.getId())
                .image(offeringRequestDTO.getImage())
                .build();

    }

    public OfferingResponseDTO toOfferingResponseDTO(Offering offering) {

        if (offering == null) {
            throw new ValidationException("Offering entity not found");
        }

        return OfferingResponseDTO.builder()
                .id(offering.getId())
                .name(offering.getName())
                .description(offering.getDescription())
                .price(offering.getPrice())
                .duration(offering.getDuration())
                .salonId(offering.getSalonId())
                .categoryId(offering.getCategoryId())
                .available(offering.isAvailable())
                .image(offering.getImage())
                .build();
    }
}
