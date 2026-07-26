package com.milano.service;

import com.milano.dto.request.SalonRequestDTO;
import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.PagedResponseDTO;
import com.milano.dto.response.SalonResponseDTO;

import java.util.UUID;

public interface SalonService {

    void createSalon(SalonRequestDTO salonRequestDTO, String Jwt);
    void updateSalon(SalonRequestDTO salonRequestDTO, UUID id, String jwt);
    SalonResponseDTO getSalonByOwnerId(String jwt);
    SalonResponseDTO getSalonById(UUID salonId);
    PagedResponseDTO<SalonResponseDTO> getAllSalons(String searchText, int page, int size);
    PagedResponseDTO<SalonResponseDTO> searchSalonByCity(String searchText, int page, int size);
}
