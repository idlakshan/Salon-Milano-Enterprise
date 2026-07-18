package com.milano.service.impl;

import com.milano.dto.request.CategoryRequestDTO;
import com.milano.dto.request.OfferingRequestDTO;
import com.milano.dto.request.SalonRequestDTO;
import com.milano.dto.response.OfferingResponseDTO;
import com.milano.dto.response.PagedResponseDTO;
import com.milano.entity.Offering;
import com.milano.exception.EntryNotFoundException;
import com.milano.repo.OfferingRepo;
import com.milano.service.OfferingService;
import com.milano.util.OfferingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferingServiceImpl implements OfferingService {

    private final OfferingRepo offeringRepo;
    private final OfferingMapper offeringMapper;

    @Override
    public void createServiceOffering(SalonRequestDTO salonRequestDTO,
                                      OfferingRequestDTO offeringRequestDTO,
                                      CategoryRequestDTO categoryRequestDTO) {
        offeringRepo.save(offeringMapper.toOffering(salonRequestDTO,offeringRequestDTO,categoryRequestDTO));

    }

    @Override
    public void updateServiceOffering(UUID id, OfferingRequestDTO offeringRequestDTO) {

        Offering offering = offeringRepo.findById(id)
                .orElseThrow(() ->
                        new EntryNotFoundException("Service not found by provided id"));

        offering.setName(offeringRequestDTO.getName());
        offering.setDescription(offeringRequestDTO.getDescription());
        offering.setPrice(offeringRequestDTO.getPrice());
        offering.setDuration(offeringRequestDTO.getDuration());
        offering.setImage(offeringRequestDTO.getImage());


        offeringRepo.save(offering);
    }

    @Override
    public Set<OfferingResponseDTO> getAllServiceOfferingsBySalonId(UUID salonId, UUID categoryId) {

        Set<Offering> offerings = offeringRepo.findBySalonId(salonId);

        if (categoryId != null) {
            offerings = offerings.stream()
                    .filter(offer ->
                            offer.getCategoryId() != null &&
                                    offer.getCategoryId().equals(categoryId)
                    )
                    .collect(Collectors.toSet());
        }

        return offerings.stream()
                .map(offeringMapper::toOfferingResponseDTO)
                .collect(Collectors.toSet());
    }


    @Override
    public Set<OfferingResponseDTO> getServicesByIds(Set<UUID> ids) {

        List<Offering> offerings = offeringRepo.findAllById(ids);

        return offerings.stream()
                .map(offeringMapper::toOfferingResponseDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public OfferingResponseDTO getOfferingById(UUID id) {
        Offering offering = offeringRepo.findById(id)
                .orElseThrow(() ->
                        new EntryNotFoundException("Service not found by provided id"));

        return offeringMapper.toOfferingResponseDTO(offering);

    }
}
