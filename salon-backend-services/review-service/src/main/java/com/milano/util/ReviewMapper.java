package com.milano.util;

import com.milano.dto.request.ReviewRequestDTO;
import com.milano.dto.response.ReviewResponseDTO;
import com.milano.dto.SalonDTO;
import com.milano.dto.UserDTO;
import com.milano.entity.Review;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReviewMapper {

    public Review toReview(ReviewRequestDTO dto, UserDTO userDTO, SalonDTO salonDTO) {
        return Review.builder()
                .reviewText(dto.getReviewText())
                .rating(dto.getRating())
                .userId(userDTO.getId())
                .salonId(salonDTO.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public ReviewResponseDTO toReviewResponseDTO(Review review) {
        return ReviewResponseDTO.builder()
                .id(review.getId())
                .reviewText(review.getReviewText())
                .rating(review.getRating())
                .salonId(review.getSalonId())
                .userId(review.getUserId())
                .createdAt(review.getCreatedAt())
                .build();
    }
}