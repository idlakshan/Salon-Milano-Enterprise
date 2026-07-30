package com.milano.service;

import com.milano.dto.request.ReviewRequestDTO;
import com.milano.dto.response.ReviewResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO, String jwt, UUID salonId);
    List<ReviewResponseDTO> getReviewsBySalonId(UUID salonId);
    ReviewResponseDTO updateReview(UUID reviewId, ReviewRequestDTO reviewRequestDTO, String jwt);
    void deleteReview(UUID reviewId, String jwt);
}