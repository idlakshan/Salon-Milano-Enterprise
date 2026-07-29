package com.milano.service.impl;

import com.milano.dto.request.CategoryRequestDTO;
import com.milano.dto.SalonDTO;
import com.milano.dto.response.CategoryResponseDTO;
import com.milano.dto.response.PagedResponseDTO;
import com.milano.entity.Category;
import com.milano.exception.EntryNotFoundException;
import com.milano.exception.UnauthorizedException;
import com.milano.repo.CategoryRepo;
import com.milano.service.CategoryService;
import com.milano.service.client.SalonFeignClient;
import com.milano.util.CategoryMapper;
import com.milano.util.StandardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final CategoryMapper categoryMapper;
    private final SalonFeignClient salonFeignClient;
    private final ObjectMapper objectMapper;

    @Override
    public void createCategory(CategoryRequestDTO categoryRequestDTO, String jwt) {

        ResponseEntity<StandardResponseDTO> salonResponse = salonFeignClient.findSalonByOwnerId(jwt);
        SalonDTO salonDTO = objectMapper.convertValue(salonResponse.getBody().getData(), SalonDTO.class);

        categoryRepo.save(categoryMapper.toCategory(categoryRequestDTO, salonDTO));
    }

    @Override
    public void deleteCategory(UUID id, String jwt) {

        ResponseEntity<StandardResponseDTO> salonResponse = salonFeignClient.findSalonByOwnerId(jwt);
        SalonDTO salonDTO = objectMapper.convertValue(salonResponse.getBody().getData(), SalonDTO.class);

        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new EntryNotFoundException("Category not found for provided id"));

        if (!category.getSalonId().equals(salonDTO.getId())) {
            throw new UnauthorizedException("You don't have permission to delete this category");
        }

        categoryRepo.deleteById(id);
    }

    @Override
    public CategoryResponseDTO getCategoryById(UUID id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new EntryNotFoundException("Category not found for provided id"));
        return categoryMapper.toCategoryResponseDTO(category);
    }

    @Override
    public PagedResponseDTO<CategoryResponseDTO> getAllCategories(String searchText, int page, int size) {
        searchText = "%" + searchText + "%";

        return PagedResponseDTO
                .<CategoryResponseDTO>builder()
                .dataCount(categoryRepo.countAllCategories(searchText))
                .dataList(categoryRepo.findAllCategories(searchText, PageRequest.of(page, size))
                        .stream().map(categoryMapper::toCategoryResponseDTO).toList()).build();
    }

    @Override
    public Set<CategoryResponseDTO> getAllCategoriesBySalon(UUID id) {
        Set<Category> categories = categoryRepo.findBySalonId(id);

        return categories.stream().map(categoryMapper::toCategoryResponseDTO).collect(Collectors.toSet());
    }

    @Override
    public void updateCategory(UUID id, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new EntryNotFoundException("Category not found for provided id"));
        category.setName(categoryRequestDTO.getName());
        category.setImage(categoryRequestDTO.getImage());
        categoryRepo.save(category);

    }

    @Override
    public CategoryResponseDTO findByIdAndSalonId(UUID id, UUID salonId) {
        Category category = categoryRepo.findByIdAndSalonIdIs(id, salonId);
        if (category == null) {
            throw new EntryNotFoundException("Category Not found");
        }
        return categoryMapper.toCategoryResponseDTO(category);
    }
}
