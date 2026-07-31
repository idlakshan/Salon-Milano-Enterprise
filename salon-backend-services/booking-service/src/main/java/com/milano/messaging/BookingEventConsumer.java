package com.milano.messaging;

import com.milano.dto.PaymentOrderDTO;
import com.milano.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventConsumer {

    private final BookingService bookingService;

    @RabbitListener(queues = "booking-queue")
    public void bookingUpdateListener(PaymentOrderDTO paymentOrder){
       // System.out.println("Received message: " + paymentOrder);
        bookingService.bookingSuccess(paymentOrder);
       // System.out.println("Booking status updated successfully for order: " + paymentOrder.getId());

    }
}