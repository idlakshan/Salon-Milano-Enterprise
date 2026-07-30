package com.milano.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milano.dto.BookingDTO;
import com.milano.dto.request.NotificationRequestDTO;
import com.milano.dto.response.NotificationResponseDTO;
import com.milano.entity.Notification;
import com.milano.exception.EntryNotFoundException;
import com.milano.repo.NotificationRepo;
import com.milano.service.NotificationService;
import com.milano.service.client.BookingFeignClient;
import com.milano.util.NotificationMapper;
import com.milano.util.StandardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepo notificationRepo;
    private final NotificationMapper notificationMapper;
    private final BookingFeignClient bookingFeignClient;
    private final ObjectMapper objectMapper;


    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO){

        Notification notification = notificationMapper.toNotification(requestDTO);
        Notification savedNotification = notificationRepo.save(notification);

        BookingDTO bookingDTO = null;

        if(savedNotification.getBookingId()!=null){

            ResponseEntity<StandardResponseDTO> response = bookingFeignClient
                    .getBookingById(savedNotification.getBookingId());

            if(response.getBody()!=null){
                bookingDTO = objectMapper.convertValue(
                                response.getBody().getData(),
                                BookingDTO.class);
            }
        }
        return notificationMapper.toResponseDTO(savedNotification, bookingDTO);
    }


    @Override
    public List<NotificationResponseDTO> getAllNotificationsByUserId(UUID userId){

        return notificationRepo.findByUserId(userId)
                .stream()
                .map(notificationMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<NotificationResponseDTO> getAllNotificationsBySalonId(UUID salonId){

        return notificationRepo.findBySalonId(salonId)
                .stream()
                .map(notificationMapper::toResponseDTO)
                .toList();
    }

    @Override
    public NotificationResponseDTO markNotificationAsRead(UUID notificationId){

        Notification notification = notificationRepo.findById(notificationId)
                        .orElseThrow(()->new EntryNotFoundException("Notification not found"));

        notification.setRead(true);
        Notification updated = notificationRepo.save(notification);

        return notificationMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteNotification(UUID notificationId){

        Notification notification = notificationRepo.findById(notificationId)
                        .orElseThrow(()->new EntryNotFoundException("Notification not found"));
        notificationRepo.delete(notification);
    }

    @Override
    public List<NotificationResponseDTO> getAllNotifications(){

        return notificationRepo.findAll()
                .stream()
                .map(notificationMapper::toResponseDTO)
                .toList();
    }
}
