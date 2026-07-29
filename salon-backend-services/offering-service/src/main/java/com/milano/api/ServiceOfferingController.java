package com.milano.api;

import com.milano.dto.response.OfferingResponseDTO;
import com.milano.service.OfferingService;
import com.milano.util.StandardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/service-offering")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final OfferingService offeringService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<StandardResponseDTO> getServicesBySalonId(
            @PathVariable UUID salonId,
            @RequestParam(required = false) UUID categoryId) {
        Set<OfferingResponseDTO> services = offeringService.getAllServiceOfferingsBySalonId(salonId, categoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("services retrieved successfully")
                        .data(services)
                        .build());

    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<StandardResponseDTO> getServiceById(@PathVariable UUID serviceId) {
        OfferingResponseDTO service = offeringService.getOfferingById(serviceId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("service retrieved successfully")
                        .data(service)
                        .build());

    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<StandardResponseDTO> getServicesByIds(@PathVariable Set<UUID> ids) {
        Set<OfferingResponseDTO> services = offeringService.getServicesByIds(ids);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("services retrieved successfully")
                        .data(services)
                        .build());

    }

}
