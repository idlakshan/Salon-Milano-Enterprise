package com.milano.service;


import com.milano.dto.request.BookingRequestDTO;
import com.milano.dto.response.BookingResponseDTO;
import com.milano.dto.PaymentDTO;
import com.milano.dto.response.SalonReportResponseDTO;
import com.milano.entity.BOOKING_STATUS;
import com.milano.entity.PAYMENT_METHOD;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingService {

    PaymentDTO createBooking(BookingRequestDTO bookingRequestDTO, String jwt, UUID salonId,
                             PAYMENT_METHOD paymentMethod);
    List<BookingResponseDTO> getBookingsByCustomer(String jwt);
    List<BookingResponseDTO> getBookingsBySalon(String jwt);
    BookingResponseDTO getBookingsById(UUID id);
    void updateBooking(UUID bookingId, BOOKING_STATUS status);
    List<BookingResponseDTO> getBookingsByDate(LocalDate date, UUID salonId);
    SalonReportResponseDTO getSalonReport(String jwt);



}
