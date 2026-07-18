package com.milano.service;

import com.milano.dto.request.CategoryRequestDTO;
import com.milano.dto.request.SalonRequestDTO;
import com.milano.dto.response.CategoryResponseDTO;
import com.milano.dto.response.PagedResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CategoryService {

    void createCategory(CategoryRequestDTO categoryRequestDTO, SalonRequestDTO salonRequestDTO);
    void deleteCategory(UUID id, UUID salonId);
    CategoryResponseDTO getCategoryById(UUID id);
    PagedResponseDTO<CategoryResponseDTO> getAllCategories(String searchText, int page, int size);
    Set<CategoryResponseDTO> getAllCategoriesBySalon(UUID id);
    void updateCategory(UUID id,CategoryRequestDTO categoryRequestDTO);
}
