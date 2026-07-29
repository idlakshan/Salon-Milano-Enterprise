package com.milano.service;

import com.milano.dto.request.OfferingRequestDTO;
import com.milano.dto.response.OfferingResponseDTO;

import java.util.Set;
import java.util.UUID;

public interface OfferingService {

    void createServiceOffering(String jwt, OfferingRequestDTO offeringRequestDTO);

    void updateServiceOffering(UUID id, OfferingRequestDTO offeringRequestDTO);

    Set<OfferingResponseDTO> getAllServiceOfferingsBySalonId(UUID salonId, UUID categoryId);

    Set<OfferingResponseDTO> getServicesByIds(Set<UUID> ids);

    OfferingResponseDTO getOfferingById(UUID id);
}
