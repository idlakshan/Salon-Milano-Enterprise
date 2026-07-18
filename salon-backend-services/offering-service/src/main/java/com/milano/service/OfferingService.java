package com.milano.service;

import com.milano.dto.request.CategoryRequestDTO;
import com.milano.dto.request.OfferingRequestDTO;
import com.milano.dto.request.SalonRequestDTO;
import com.milano.dto.response.OfferingResponseDTO;
import com.milano.dto.response.PagedResponseDTO;

import java.util.Set;
import java.util.UUID;

public interface OfferingService {

    void createServiceOffering(SalonRequestDTO salonRequestDTO, OfferingRequestDTO offeringRequestDTO,
                               CategoryRequestDTO categoryRequestDTO);

    void updateServiceOffering(UUID id, OfferingRequestDTO offeringRequestDTO);

    Set<OfferingResponseDTO> getAllServiceOfferingsBySalonId(UUID salonId, UUID categoryId);

    Set<OfferingResponseDTO> getServicesByIds(Set<UUID> ids);

    OfferingResponseDTO getOfferingById(UUID id);
}
