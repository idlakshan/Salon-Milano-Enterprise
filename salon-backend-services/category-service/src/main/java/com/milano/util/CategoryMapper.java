package com.milano.util;

import com.milano.dto.request.CategoryRequestDTO;
import com.milano.dto.request.SalonRequestDTO;
import com.milano.dto.response.CategoryResponseDTO;
import com.milano.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toCategory(CategoryRequestDTO categoryRequestDTO, SalonRequestDTO salonRequestDTO) {
        return Category.builder()
                .name(categoryRequestDTO.getName())
                .image(categoryRequestDTO.getImage())
                .salonId(salonRequestDTO.getId())
                .build();
    }

    public CategoryResponseDTO toCategoryResponseDTO(Category category) {
        return CategoryResponseDTO.builder().id(category.getId())
                .name(category.getName())
                .image(category.getImage())
                .salonId(category.getSalonId())
                .build();
    }
}
