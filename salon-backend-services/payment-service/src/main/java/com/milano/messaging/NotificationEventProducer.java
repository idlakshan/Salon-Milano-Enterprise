package com.milano.messaging;

import com.milano.dto.NotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotificationEventProducer {

    private final RabbitTemplate rabbitTemplate;


    public void sentNotificationEvent(UUID bookingId,
                                      UUID userId,
                                      UUID salonId) {
        NotificationDTO notification = new NotificationDTO();
        notification.setBookingId(bookingId);
        notification.setSalonId(salonId);
        notification.setUserId(userId);
        notification.setDescription("new booking got confirmed");
        notification.setType("BOOKING");

        rabbitTemplate.convertAndSend("notification-queue", notification);
    }
}
