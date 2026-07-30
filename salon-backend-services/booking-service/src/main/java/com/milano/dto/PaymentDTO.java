package com.milano.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PaymentDTO {
    private String paymentLinkUrl;
    private String paymentLinkId;
}
