package com.milano.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milano.dto.SalonDTO;
import com.milano.dto.UserDTO;
import com.milano.dto.request.ReviewRequestDTO;
import com.milano.dto.response.ReviewResponseDTO;
import com.milano.entity.Review;
import com.milano.exception.EntryNotFoundException;
import com.milano.exception.ValidationException;
import com.milano.repo.ReviewRepo;
import com.milano.service.ReviewService;
import com.milano.service.client.SalonFeignClient;
import com.milano.service.client.UserFeignClient;
import com.milano.util.ReviewMapper;
import com.milano.util.StandardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepo reviewRepo;
    private final ReviewMapper reviewMapper;
    private final UserFeignClient userFeignClient;
    private final SalonFeignClient salonFeignClient;
    private final ObjectMapper objectMapper;

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO, String jwt, UUID salonId) {

        ResponseEntity<StandardResponseDTO> userResponse = userFeignClient.getUserProfile(jwt);
        UserDTO userDTO = objectMapper.convertValue(userResponse.getBody().getData(), UserDTO.class);

        ResponseEntity<StandardResponseDTO> salonResponse = salonFeignClient.findSalonById(salonId);
        SalonDTO salonDTO = objectMapper.convertValue(salonResponse.getBody().getData(), SalonDTO.class);

        Review review = reviewMapper.toReview(reviewRequestDTO, userDTO, salonDTO);
        Review savedReview = reviewRepo.save(review);

        return reviewMapper.toReviewResponseDTO(savedReview);
    }

    @Override
    public List<ReviewResponseDTO> getReviewsBySalonId(UUID salonId) {
        List<Review> reviews = reviewRepo.findReviewsBySalonId(salonId);
        return reviews.stream()
                .map(reviewMapper::toReviewResponseDTO)
                .toList();
    }

    @Override
    public ReviewResponseDTO updateReview(UUID reviewId, ReviewRequestDTO reviewRequestDTO, String jwt) {
        ResponseEntity<StandardResponseDTO> userResponse = userFeignClient.getUserProfile(jwt);
        UserDTO userDTO = objectMapper.convertValue(userResponse.getBody().getData(), UserDTO.class);

        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new EntryNotFoundException("Review not found with provided ID: " + reviewId));

        if (!review.getUserId().equals(userDTO.getId())) {
            throw new ValidationException("You do not have permission to update this review");
        }

        review.setReviewText(reviewRequestDTO.getReviewText());
        review.setRating(reviewRequestDTO.getRating());

        Review updatedReview = reviewRepo.save(review);
        return reviewMapper.toReviewResponseDTO(updatedReview);
    }

    @Override
    public void deleteReview(UUID reviewId, String jwt) {
        ResponseEntity<StandardResponseDTO> userResponse = userFeignClient.getUserProfile(jwt);
        UserDTO userDTO = objectMapper.convertValue(userResponse.getBody().getData(), UserDTO.class);

        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new EntryNotFoundException("Review not found with provided ID: " + reviewId));

        if (!review.getUserId().equals(userDTO.getId())) {
            throw new ValidationException("You do not have permission to delete this review");
        }

        reviewRepo.delete(review);
    }


}