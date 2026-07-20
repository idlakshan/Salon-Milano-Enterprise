package com.milano.util;

import com.milano.dto.request.BookingRequestDTO;
import com.milano.dto.request.SalonRequestDTO;
import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.BookingResponseDTO;
import com.milano.dto.response.SalonReportResponseDTO;
import com.milano.entity.BOOKING_STATUS;
import com.milano.entity.Booking;
import com.milano.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Component
public class BookingMapper {

    public Booking toBooking(BookingRequestDTO bookingRequestDTO, UserRequestDTO userRequestDTO,
                             SalonRequestDTO salonRequestDTO,double totalPrice,  Set<UUID> idList,
                             LocalDateTime bookingEndTime){


        return Booking.builder()
                .salonId(salonRequestDTO.getId())
                .customerId(userRequestDTO.getId())
                .startTime(bookingRequestDTO.getStartTime())
                .endTime(bookingEndTime)
                .serviceIds(idList)
                .status(BOOKING_STATUS.PENDING)
                .totalPrice(totalPrice)
                .build();

    }

    public BookingResponseDTO toBookingResponseDTO(Booking booking) {

        if (booking == null) throw new ValidationException("Booking not found");

        return BookingResponseDTO.builder()
                .id(booking.getId())
                .salonId(booking.getSalonId())
                .customerId(booking.getCustomerId())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .serviceId(booking.getServiceIds())
                .status(booking.getStatus())
                .totalPrice(booking.getTotalPrice())
                .build();
    }

    public SalonReportResponseDTO toSalonReportResponseDTO(
            UUID salonId,
            double totalEarnings,
            int totalBookings,
            int cancelledBookings,
            double totalRefund
    ) {

        return SalonReportResponseDTO.builder()
                .salonId(salonId)
                .totalEarnings(totalEarnings)
                .totalBookings(totalBookings)
                .cancelledBookings(cancelledBookings)
                .totalRefund(totalRefund)
                .build();
    }

}
