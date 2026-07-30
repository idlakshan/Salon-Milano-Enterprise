package com.milano.api;

import com.milano.dto.request.BookingRequestDTO;
import com.milano.dto.response.BookingResponseDTO;
import com.milano.dto.PaymentDTO;
import com.milano.dto.response.SalonReportResponseDTO;
import com.milano.entity.BOOKING_STATUS;
import com.milano.entity.PAYMENT_METHOD;
import com.milano.service.BookingService;
import com.milano.util.StandardResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<StandardResponseDTO> createBooking(
            @RequestParam UUID salonId,
            @RequestParam PAYMENT_METHOD paymentMethod,
            @RequestBody @Valid BookingRequestDTO bookingRequestDTO,
            @RequestHeader("Authorization") String jwt) {

        PaymentDTO paymentResponse = bookingService.createBooking(bookingRequestDTO, jwt, salonId, paymentMethod);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(StandardResponseDTO.builder()
                        .code(201)
                        .message("Booking created successfully")
                        .data(paymentResponse)
                        .build());
    }


    @GetMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> getBookingById(
            @PathVariable UUID id) {

        BookingResponseDTO booking = bookingService.getBookingsById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Booking retrieved successfully")
                        .data(booking)
                        .build());
    }


    @GetMapping("/customer")
    public ResponseEntity<StandardResponseDTO> getBookingsByCustomer(@RequestHeader("Authorization") String jwt) {

        List<BookingResponseDTO> bookings =
                bookingService.getBookingsByCustomer(jwt);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Customer bookings retrieved successfully")
                        .data(bookings)
                        .build());
    }


    @GetMapping("/salon")
    public ResponseEntity<StandardResponseDTO> getBookingsBySalon(
            @RequestHeader("Authorization") String jwt) {

        List<BookingResponseDTO> bookings =
                bookingService.getBookingsBySalon(jwt);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Salon bookings retrieved successfully")
                        .data(bookings)
                        .build());
    }


    @GetMapping("slots/salon/{salonId}/date/{date}")
    public ResponseEntity<StandardResponseDTO> getBookingsByDate(
            @PathVariable UUID salonId,
            @RequestParam(required = false) LocalDate date) {

        List<BookingResponseDTO> bookings =
                bookingService.getBookingsByDate(date, salonId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Bookings retrieved successfully")
                        .data(bookings)
                        .build());
    }


    @PutMapping("/{bookingId}/status")
    public ResponseEntity<StandardResponseDTO> updateBooking(
            @PathVariable UUID bookingId,
            @RequestParam BOOKING_STATUS status) {

        bookingService.updateBooking(bookingId, status);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Booking status updated successfully")
                        .data(null)
                        .build());
    }


    @GetMapping("/report")
    public ResponseEntity<StandardResponseDTO> getSalonReport(@RequestHeader("Authorization") String jwt) {

        SalonReportResponseDTO report =
                bookingService.getSalonReport(jwt);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Salon report retrieved successfully")
                        .data(report)
                        .build());
    }
}