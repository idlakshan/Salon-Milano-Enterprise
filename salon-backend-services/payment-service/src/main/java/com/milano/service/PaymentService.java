package com.milano.service;

import com.milano.dto.BookingDTO;
import com.milano.dto.UserDTO;
import com.milano.dto.response.PaymentOrderResponseDTO;
import com.milano.dto.response.PaymentResponseDTO;
import com.milano.entity.PAYMENT_METHOD;
import com.stripe.exception.StripeException;

import java.util.UUID;

public interface PaymentService {

    PaymentResponseDTO createOrder (String jwt, BookingDTO bookingRequestDTO,
                                    PAYMENT_METHOD paymentMethod);

    PaymentOrderResponseDTO getPaymentOrderById(UUID id);

    PaymentOrderResponseDTO getPaymentOrderByPaymentId(String paymentId);

    String createStripePaymentLink(UserDTO userRequestDTO, double amount, UUID orderId) throws StripeException;

     boolean proceedPayment(String paymentId, String paymentLinkId);

}
