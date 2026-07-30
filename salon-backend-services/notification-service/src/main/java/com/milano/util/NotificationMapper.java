package com.milano.util;


import com.milano.dto.BookingDTO;
import com.milano.dto.request.NotificationRequestDTO;
import com.milano.dto.response.NotificationResponseDTO;
import com.milano.entity.Notification;
import org.springframework.stereotype.Component;


@Component
public class NotificationMapper {


    public Notification toNotification(NotificationRequestDTO dto){

        return Notification.builder()
                .type(dto.getType())
                .description(dto.getDescription())
                .userId(dto.getUserId())
                .bookingId(dto.getBookingId())
                .salonId(dto.getSalonId())
                .build();
    }



    public NotificationResponseDTO toResponseDTO(Notification notification){

        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .type(notification.getType())
                .description(notification.getDescription())
                .read(notification.isRead())
                .userId(notification.getUserId())
                .bookingId(notification.getBookingId())
                .salonId(notification.getSalonId())
                .createdAt(notification.getCreatedAt())
                .build();

    }



    public NotificationResponseDTO toResponseDTO(Notification notification, BookingDTO bookingDTO){

        NotificationResponseDTO response =
                toResponseDTO(notification);

        response.setBooking(bookingDTO);

        return response;
    }

}