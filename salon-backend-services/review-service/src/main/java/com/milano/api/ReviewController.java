package com.milano.api;

import com.milano.dto.request.ReviewRequestDTO;
import com.milano.dto.response.ReviewResponseDTO;
import com.milano.service.ReviewService;
import com.milano.util.StandardResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/salon/{salonId}")
    public ResponseEntity<StandardResponseDTO> createReview(
            @PathVariable UUID salonId,
            @RequestBody @Valid ReviewRequestDTO reviewRequestDTO,
            @RequestHeader("Authorization") String jwt) {

        ReviewResponseDTO createdReview = reviewService.createReview(reviewRequestDTO, jwt, salonId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(StandardResponseDTO.builder()
                        .code(201)
                        .message("Review created successfully")
                        .data(createdReview)
                        .build());
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<StandardResponseDTO> getReviewsBySalonId(@PathVariable UUID salonId) {

        List<ReviewResponseDTO> reviews = reviewService.getReviewsBySalonId(salonId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Reviews retrieved successfully")
                        .data(reviews)
                        .build());
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<StandardResponseDTO> updateReview(
            @PathVariable UUID reviewId,
            @RequestBody @Valid ReviewRequestDTO reviewRequestDTO,
            @RequestHeader("Authorization") String jwt) {

        ReviewResponseDTO updatedReview = reviewService.updateReview(reviewId, reviewRequestDTO, jwt);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Review updated successfully")
                        .data(updatedReview)
                        .build());
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<StandardResponseDTO> deleteReview(
            @PathVariable UUID reviewId,
            @RequestHeader("Authorization") String jwt) {

        reviewService.deleteReview(reviewId, jwt);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Review deleted successfully")
                        .data(null)
                        .build());
    }
}