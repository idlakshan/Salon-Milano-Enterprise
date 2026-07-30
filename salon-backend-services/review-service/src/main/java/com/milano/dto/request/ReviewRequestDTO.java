package com.milano.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDTO {
    private String reviewText;
    private double rating;
}
