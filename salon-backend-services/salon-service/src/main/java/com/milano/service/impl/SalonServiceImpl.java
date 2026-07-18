package com.milano.service.impl;

import com.milano.dto.request.SalonRequestDTO;
import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.PagedResponseDTO;
import com.milano.dto.response.SalonResponseDTO;
import com.milano.entity.Salon;
import com.milano.exception.EntryNotFoundException;
import com.milano.exception.UnauthorizedException;
import com.milano.repo.SalonRepo;
import com.milano.service.SalonService;
import com.milano.util.SalonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    private final SalonRepo salonRepo;
    private final SalonMapper salonMapper;

    @Override
    public void createSalon(SalonRequestDTO salonRequestDTO, UserRequestDTO userRequestDTO) {
        salonRepo.save(salonMapper.toSalon(salonRequestDTO, userRequestDTO));
    }

    @Override
    public void updateSalon(SalonRequestDTO salonRequestDTO, UserRequestDTO userRequestDTO, UUID id) {
        Salon salon = salonRepo.findById(id).orElseThrow(() ->
                new EntryNotFoundException("Salon not found by provided id"));

        if (!salonRequestDTO.getOwnerId().equals(userRequestDTO.getId())) {
            throw new UnauthorizedException("You are not authorized to update this salon.");
        }

        salon.setName(salonRequestDTO.getName());
        salon.setAddress(salonRequestDTO.getAddress());
        salon.setPhoneNumber(salonRequestDTO.getPhoneNumber());
        salon.setEmail(salonRequestDTO.getEmail());
        salon.setCity(salonRequestDTO.getCity());
        salon.setOpen(salonRequestDTO.isOpen());
        salon.setHomeService(salonRequestDTO.isHomeService());
        salon.setActive(salonRequestDTO.isActive());
        salon.setOpenTime(salonRequestDTO.getOpenTime());
        salon.setCloseTime(salonRequestDTO.getCloseTime());
        salon.setImages(salonRequestDTO.getImages());

        salonRepo.save(salon);

    }

    @Override
    public SalonResponseDTO getSalonByOwnerId(UUID ownerId) {

        Salon salon = salonRepo.findSalonByOwnerId(ownerId);
        if(salon==null){
            throw new EntryNotFoundException("Salon not found");
        }
        return salonMapper.toSalonResponseDTO(salon);
    }

    @Override
    public SalonResponseDTO getSalonById(UUID salonId) {
        Salon salon = salonRepo.findById(salonId).orElseThrow(() ->
                new EntryNotFoundException("Salon not found by provided id"));
        return salonMapper.toSalonResponseDTO(salon);
    }

    @Override
    public PagedResponseDTO<SalonResponseDTO> getAllSalons(String searchText, int page, int size) {
        searchText = "%" + searchText + "%";

        return PagedResponseDTO.<SalonResponseDTO>builder().dataCount(salonRepo.countAllSalons(searchText)).dataList(salonRepo.findAllSalons(searchText, PageRequest.of(page, size)).stream().map(salonMapper::toSalonResponseDTO).toList()).build();

    }

    @Override
    public PagedResponseDTO<SalonResponseDTO> searchSalonByCity(String searchText, int page, int size) {
        searchText = "%" + searchText + "%";

        return PagedResponseDTO.<SalonResponseDTO>builder().dataCount(salonRepo.countAllSalons(searchText)).dataList(salonRepo.searchSalons(searchText, PageRequest.of(page, size)).stream().map(salonMapper::toSalonResponseDTO).toList()).build();
    }
}
