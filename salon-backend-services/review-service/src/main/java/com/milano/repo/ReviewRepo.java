package com.milano.repo;

import com.milano.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.UUID;

@EnableJpaRepositories
public interface ReviewRepo extends JpaRepository<Review, UUID> {
    List<Review> findReviewsByUserId(UUID userId);
    List<Review> findReviewsBySalonId(UUID productId);

}
