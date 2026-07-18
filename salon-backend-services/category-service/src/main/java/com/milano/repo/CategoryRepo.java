package com.milano.repo;

import com.milano.dto.response.CategoryResponseDTO;
import com.milano.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {

    Set<Category> findBySalonId(UUID salonId);

    @Query(value = "SELECT * FROM category WHERE name LIKE ?1", nativeQuery = true)
    Page<Category> findAllCategories(String searchText, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM salons WHERE name LIKE ?1",
            nativeQuery = true)
    long countAllCategories(String searchText);
}
