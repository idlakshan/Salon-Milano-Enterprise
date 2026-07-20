package com.milano.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PaymentResponseDTO {
    private String paymentLinkUrl;
    private String paymentLinkId;
}
