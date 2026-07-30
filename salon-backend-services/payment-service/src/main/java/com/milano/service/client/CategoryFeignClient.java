package com.milano.service.client;

import com.milano.util.StandardResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "CATEGORY-SERVICE",
        path = "/api/v1/categories/salon-owner")
public interface CategoryFeignClient {

//    @GetMapping("/{id}")
//    ResponseEntity<StandardResponseDTO> getCategoryById(@PathVariable UUID id);

    @GetMapping("/salon/{salonId}/category/{id}")
    ResponseEntity<StandardResponseDTO> getCategoryByIdAndSalon(
            @PathVariable("id") UUID id,
            @PathVariable("salonId") UUID salonId);

    }
