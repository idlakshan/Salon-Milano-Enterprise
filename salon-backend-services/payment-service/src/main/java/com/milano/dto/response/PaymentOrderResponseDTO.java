package com.milano.dto.response;

import com.milano.entity.PAYMENT_METHOD;
import com.milano.entity.PAYMENT_STATUS;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PaymentOrderResponseDTO {

    private UUID id;
    private double amount;
    private PAYMENT_STATUS status;
    private PAYMENT_METHOD paymentMethod;
    private String paymentLinkId;
    private UUID userId;
    private UUID bookingId;
    private UUID salonId;
}
