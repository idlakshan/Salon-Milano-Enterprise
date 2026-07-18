package com.milano.api;

import com.milano.dto.request.CategoryRequestDTO;
import com.milano.dto.request.SalonRequestDTO;
import com.milano.service.CategoryService;
import com.milano.util.StandardResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories/salon-owner")
@RequiredArgsConstructor
public class SalonCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<StandardResponseDTO> createCategory(
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {

        SalonRequestDTO salonRequestDTO = new SalonRequestDTO();
        salonRequestDTO.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));

        categoryService.createCategory(categoryRequestDTO, salonRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(StandardResponseDTO.builder()
                        .code(201)
                        .message("Category created successfully")
                        .data(null)
                        .build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> deleteCategory(@PathVariable UUID id) {

        SalonRequestDTO salonRequestDTO = new SalonRequestDTO();
        salonRequestDTO.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));

        categoryService.deleteCategory(id,salonRequestDTO.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Category deleted successfully")
                        .data(null)
                        .build());
    }

}
