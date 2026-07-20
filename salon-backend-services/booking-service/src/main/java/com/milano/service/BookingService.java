package com.milano.service;


import com.milano.dto.request.BookingRequestDTO;
import com.milano.dto.request.OfferingRequestDTO;
import com.milano.dto.request.SalonRequestDTO;
import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.BookingResponseDTO;
import com.milano.dto.response.SalonReportResponseDTO;
import com.milano.entity.BOOKING_STATUS;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface BookingService {

    void createBooking(BookingRequestDTO bookingRequestDTO, UserRequestDTO userRequestDTO, SalonRequestDTO salonRequestDTO,
                       Set<OfferingRequestDTO> offeringRequestDTOS);

    List<BookingResponseDTO> getBookingsByCustomer(UUID customerId);
    List<BookingResponseDTO> getBookingsBySalon(UUID salonId);
    BookingResponseDTO getBookingsById(UUID id);
    void updateBooking(UUID bookingId, BOOKING_STATUS status);
    List<BookingResponseDTO> getBookingsByDate(LocalDate date, UUID salonId);
    SalonReportResponseDTO getSalonReport(UUID salonId);



}
