package com.milano.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milano.dto.CategoryDTO;
import com.milano.dto.SalonDTO;
import com.milano.dto.request.OfferingRequestDTO;
import com.milano.dto.response.OfferingResponseDTO;
import com.milano.entity.Offering;
import com.milano.exception.EntryNotFoundException;
import com.milano.repo.OfferingRepo;
import com.milano.service.OfferingService;
import com.milano.service.client.CategoryFeignClient;
import com.milano.service.client.SalonFeignClient;
import com.milano.util.OfferingMapper;
import com.milano.util.StandardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final SalonFeignClient salonFeignClient;
    private final CategoryFeignClient categoryFeignClient;
    private final ObjectMapper objectMapper;

    @Override
    public void createServiceOffering(String jwt, OfferingRequestDTO offeringRequestDTO) {

        ResponseEntity<StandardResponseDTO> salonResponse = salonFeignClient.findSalonByOwnerId(jwt);
        SalonDTO salonDTO = objectMapper.convertValue(salonResponse.getBody().getData(), SalonDTO.class);

        ResponseEntity<StandardResponseDTO> categoryResponse = categoryFeignClient
                .getCategoryByIdAndSalon(offeringRequestDTO.getCategory(), salonDTO.getId());

        CategoryDTO categoryDTO = objectMapper.convertValue(
                categoryResponse.getBody().getData(),
                CategoryDTO.class);

        Offering offering = offeringMapper.toOffering(salonDTO, offeringRequestDTO, categoryDTO);
        offeringRepo.save(offering);
    }

    @Override
    public void updateServiceOffering(UUID id, OfferingRequestDTO offeringRequestDTO) {

        Offering offering = offeringRepo.findById(id).orElseThrow(() -> new EntryNotFoundException("Service not found by provided id"));

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
            offerings = offerings.stream().filter(offer -> offer.getCategoryId() != null && offer.getCategoryId().equals(categoryId)).collect(Collectors.toSet());
        }

        return offerings.stream().map(offeringMapper::toOfferingResponseDTO).collect(Collectors.toSet());
    }


    @Override
    public Set<OfferingResponseDTO> getServicesByIds(Set<UUID> ids) {

        List<Offering> offerings = offeringRepo.findAllById(ids);

        return offerings.stream().map(offeringMapper::toOfferingResponseDTO).collect(Collectors.toSet());
    }

    @Override
    public OfferingResponseDTO getOfferingById(UUID id) {
        Offering offering = offeringRepo.findById(id).orElseThrow(() -> new EntryNotFoundException("Service not found by provided id"));

        return offeringMapper.toOfferingResponseDTO(offering);

    }
}