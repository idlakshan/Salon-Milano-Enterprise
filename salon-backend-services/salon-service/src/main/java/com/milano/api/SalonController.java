package com.milano.api;

import com.milano.dto.request.SalonRequestDTO;
import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.PagedResponseDTO;
import com.milano.dto.response.SalonResponseDTO;
import com.milano.service.SalonService;
import com.milano.util.StandardResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/salons")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;

    @PostMapping
    public ResponseEntity<StandardResponseDTO> createSalon(@RequestBody @Valid SalonRequestDTO salonRequestDTO) {

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        salonService.createSalon(salonRequestDTO, userRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(StandardResponseDTO.builder()
                        .code(201)
                        .message("Salon created successfully")
                        .data(null)
                        .build());
    }

    @PatchMapping("/{salonId}")
    public ResponseEntity<StandardResponseDTO> updateSalon(@PathVariable UUID salonId,
                                                           @RequestBody @Valid SalonRequestDTO salonRequestDTO) {

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        salonService.updateSalon(salonRequestDTO, userRequestDTO, salonId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Salon updated successfully")
                        .data(null)
                        .build());
    }

    @GetMapping
    public ResponseEntity<StandardResponseDTO> getSalons(
            @RequestParam(defaultValue = "") String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponseDTO<SalonResponseDTO> result = salonService.getAllSalons(searchText, page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Salons retrieved successfully")
                        .data(result)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> findSalonById(@PathVariable UUID id) {
        SalonResponseDTO salon = salonService.getSalonById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Salon retrieved successfully")
                        .data(salon)
                        .build());
    }

    @GetMapping("/search")
    public ResponseEntity<StandardResponseDTO> searchSalon(
            @RequestParam("city") String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponseDTO<SalonResponseDTO> result = salonService.searchSalonByCity(searchText, page, size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Salons retrieved successfully")
                        .data(result)
                        .build());
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<StandardResponseDTO> findSalonByOwnerId(
            @PathVariable UUID ownerId) {
        SalonResponseDTO salon = salonService.getSalonByOwnerId(ownerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Salon retrieved successfully")
                        .data(salon)
                        .build());
    }

}
