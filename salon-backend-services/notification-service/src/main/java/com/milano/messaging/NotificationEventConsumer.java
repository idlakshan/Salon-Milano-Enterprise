package com.milano.messaging;

import com.milano.dto.request.NotificationRequestDTO;
import com.milano.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "notification-queue")
    public void sentBookingUpdateEvent(NotificationRequestDTO notificationRequestDTO){
        notificationService.createNotification(notificationRequestDTO);
    }
}
