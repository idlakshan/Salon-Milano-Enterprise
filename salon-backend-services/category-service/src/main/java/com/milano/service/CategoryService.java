package com.milano.service;

import com.milano.dto.request.CategoryRequestDTO;
import com.milano.dto.SalonDTO;
import com.milano.dto.response.CategoryResponseDTO;
import com.milano.dto.response.PagedResponseDTO;

import java.util.Set;
import java.util.UUID;

public interface CategoryService {

    void createCategory(CategoryRequestDTO categoryRequestDTO, String jwt);
    void deleteCategory(UUID id, String jwt);
    CategoryResponseDTO getCategoryById(UUID id);
    PagedResponseDTO<CategoryResponseDTO> getAllCategories(String searchText, int page, int size);
    Set<CategoryResponseDTO> getAllCategoriesBySalon(UUID id);
    void updateCategory(UUID id,CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO findByIdAndSalonId(UUID id,UUID salonId);
}
