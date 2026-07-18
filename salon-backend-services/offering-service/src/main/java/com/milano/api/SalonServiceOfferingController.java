package com.milano.api;

import com.milano.dto.request.CategoryRequestDTO;
import com.milano.dto.request.OfferingRequestDTO;
import com.milano.dto.request.SalonRequestDTO;
import com.milano.service.OfferingService;
import com.milano.util.StandardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/service-offering/salon-owner")
@RequiredArgsConstructor
public class SalonServiceOfferingController {

    private final OfferingService offeringService;

    @PostMapping
    public ResponseEntity<StandardResponseDTO> createService(@RequestBody OfferingRequestDTO offeringRequestDTO) {

        SalonRequestDTO salonRequestDTO = new SalonRequestDTO();
        salonRequestDTO.setId(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000")
        );

        CategoryRequestDTO categoryRequestDTO = new CategoryRequestDTO();
        categoryRequestDTO.setId(offeringRequestDTO.getCategory());

        offeringService.createServiceOffering(salonRequestDTO, offeringRequestDTO, categoryRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(StandardResponseDTO.builder()
                        .code(201)
                        .message("services created successfully")
                        .data(null)
                        .build());

    }

    @PostMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> updateService(@PathVariable UUID id,
                                                             @RequestBody OfferingRequestDTO offeringRequestDTO) {

        offeringService.updateServiceOffering(id, offeringRequestDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(201)
                        .message("services Updated successfully")
                        .data(null)
                        .build());

    }
}
