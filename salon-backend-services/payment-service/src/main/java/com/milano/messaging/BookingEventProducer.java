package com.milano.messaging;

import com.milano.dto.response.PaymentOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendBookingUpdateEvent(PaymentOrderResponseDTO paymentOrderResponseDTO) {
        rabbitTemplate.convertAndSend("booking-queue", paymentOrderResponseDTO);
    }
}