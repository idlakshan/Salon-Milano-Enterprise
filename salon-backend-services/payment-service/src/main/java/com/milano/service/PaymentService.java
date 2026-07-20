package com.milano.service;

import com.milano.dto.request.BookingRequestDTO;
import com.milano.dto.request.PaymentOrderRequestDTO;
import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.PaymentOrderResponseDTO;
import com.milano.dto.response.PaymentResponseDTO;
import com.milano.entity.PAYMENT_METHOD;
import com.stripe.exception.StripeException;

import java.util.UUID;

public interface PaymentService {

    PaymentResponseDTO createOrder (UserRequestDTO userRequestDTO, BookingRequestDTO bookingRequestDTO,
                                    PAYMENT_METHOD paymentMethod);

    PaymentOrderResponseDTO getPaymentOrderById(UUID id);

    PaymentOrderResponseDTO getPaymentOrderByPaymentId(String paymentId);

    String createStripePaymentLink(UserRequestDTO userRequestDTO, double amount, UUID orderId) throws StripeException;

     boolean proceedPayment(String paymentId, String paymentLinkId);

}
