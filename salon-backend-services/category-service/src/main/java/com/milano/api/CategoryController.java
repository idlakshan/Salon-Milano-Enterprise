package com.milano.api;

import com.milano.dto.request.CategoryRequestDTO;
import com.milano.dto.response.CategoryResponseDTO;
import com.milano.dto.response.PagedResponseDTO;
import com.milano.service.CategoryService;
import com.milano.util.StandardResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PatchMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> updateCategory(
            @PathVariable UUID id,
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {

        categoryService.updateCategory(id, categoryRequestDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Category updated successfully")
                        .data(null)
                        .build());
    }



    @GetMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> getCategoryById(@PathVariable UUID id) {

        CategoryResponseDTO category = categoryService.getCategoryById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Category retrieved successfully")
                        .data(category)
                        .build());
    }

    @GetMapping
    public ResponseEntity<StandardResponseDTO> getAllCategories(
            @RequestParam(defaultValue = "") String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PagedResponseDTO<CategoryResponseDTO> result =
                categoryService.getAllCategories(searchText, page, size);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Categories retrieved successfully")
                        .data(result)
                        .build());
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<StandardResponseDTO> getCategoriesBySalon(@PathVariable UUID salonId) {

        Set<CategoryResponseDTO> categories = categoryService.getAllCategoriesBySalon(salonId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Categories retrieved successfully")
                        .data(categories)
                        .build());
    }
}