package com.milano.repo;

import com.milano.entity.Salon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.UUID;

@EnableJpaRepositories
public interface SalonRepo extends JpaRepository<Salon, UUID> {

    Salon findSalonByOwnerId(UUID ownerId);

    @Query(value = "SELECT * FROM salons WHERE name LIKE ?1", nativeQuery = true)
    Page<Salon> findAllSalons(String searchText, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM salons WHERE name LIKE ?1",
            nativeQuery = true)
    long countAllSalons(String searchText);


    @Query(
            value = "SELECT * FROM salons " +
                    "WHERE (name LIKE ?1 OR address LIKE ?1 OR city LIKE ?1) " +
                    "AND active = true",
            nativeQuery = true
    )
    Page<Salon> searchSalons(String searchText, Pageable pageable);

}
