package com.milano.service.impl;

import com.milano.dto.request.BookingRequestDTO;
import com.milano.dto.request.PaymentOrderRequestDTO;
import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.PaymentOrderResponseDTO;
import com.milano.dto.response.PaymentResponseDTO;
import com.milano.entity.PAYMENT_METHOD;
import com.milano.entity.PAYMENT_STATUS;
import com.milano.entity.PaymentOrder;
import com.milano.exception.EntryNotFoundException;
import com.milano.exception.PaymentException;
import com.milano.repo.PaymentRepo;
import com.milano.service.PaymentService;
import com.milano.util.PaymentOrderMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private final PaymentOrderMapper paymentOrderMapper;

    @Value("${stripe.api.secret}")
    private String stripeSecretKey;

    @Override
    public PaymentResponseDTO createOrder(UserRequestDTO userRequestDTO,
                                          BookingRequestDTO bookingRequestDTO,
                                          PAYMENT_METHOD paymentMethod) {

        // 1. මුලින්ම pending order එක සේව් කරනවා (දැන් link id එක null වුණාට කමක් නැහැ)
        PaymentOrder savedOrder = paymentRepo.save(paymentOrderMapper.toPaymentOrder(
                userRequestDTO,
                bookingRequestDTO,
                paymentMethod));

        String paymentUrl = null;

        if (paymentMethod == PAYMENT_METHOD.STRIPE) {
            try {
                // 2. Stripe එකෙන් link එක ජෙනරේට් කරනවා
                paymentUrl = createStripePaymentLink(
                        userRequestDTO,
                        savedOrder.getAmount(),
                        savedOrder.getId()
                );
            } catch (StripeException e) {
                throw new PaymentException("Unable to create Stripe payment link");
            }
        }

        return PaymentResponseDTO.builder()
                .paymentLinkUrl(paymentUrl)
                .paymentLinkId(savedOrder.getId().toString())
                .build();
    }

    @Override
    public PaymentOrderResponseDTO getPaymentOrderById(UUID id) {
        PaymentOrder paymentOrder = paymentRepo.findById(id)
                .orElseThrow(() -> new EntryNotFoundException("Payment order not found"));

        return paymentOrderMapper.toPaymentOrderResponseDTO(paymentOrder);

    }

    @Override
    public PaymentOrderResponseDTO getPaymentOrderByPaymentId(String paymentId) {
        PaymentOrder paymentOrder =
                paymentRepo.findByPaymentLinkIdIs(paymentId);

        if (paymentOrder == null) {
            throw new EntryNotFoundException("Payment order not found");
        }

        return paymentOrderMapper.toPaymentOrderResponseDTO(paymentOrder);
    }

    @Override
    public String createStripePaymentLink(UserRequestDTO userRequestDTO,
                                          double amount,
                                          UUID orderId) throws StripeException {

        Stripe.apiKey = stripeSecretKey;
        long amountInCents = Math.round(amount * 100);

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5173/payment-success/" + orderId)
                .setCancelUrl("http://localhost:5173/payment-cancel")
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amountInCents)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Salon Appointment Booking")
                                        .build()).build())
                        .build())
                .build();

        Session session = Session.create(params);

        PaymentOrder paymentOrder = paymentRepo.findById(orderId)
                .orElseThrow(() -> new EntryNotFoundException("Payment order not found"));

        paymentOrder.setPaymentLinkId(session.getId());
        paymentRepo.save(paymentOrder);

        return session.getUrl();
    }

    @Override
    public boolean proceedPayment(String orderId, String paymentLinkId) {

        PaymentOrder paymentOrder = paymentRepo.findById(UUID.fromString(orderId))
                .orElseThrow(() -> new EntryNotFoundException("Payment order not found"));

        if (paymentOrder.getStatus() != PAYMENT_STATUS.PENDING) {
            return false;
        }

        if (paymentOrder.getPaymentMethod() == PAYMENT_METHOD.STRIPE) {
            try {
                Stripe.apiKey = stripeSecretKey;
                Session session = Session.retrieve(paymentOrder.getPaymentLinkId());

                if ("paid".equals(session.getPaymentStatus())) {
                    paymentOrder.setStatus(PAYMENT_STATUS.SUCCESS);
                    paymentRepo.save(paymentOrder);
                    return true;
                }
            } catch (StripeException e) {
                throw new PaymentException("Stripe verification failed");
            }
        }
        return false;
    }
}
