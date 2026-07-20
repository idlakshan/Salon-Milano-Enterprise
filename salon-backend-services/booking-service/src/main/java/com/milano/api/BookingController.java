package com.milano.api;

import com.milano.dto.request.BookingRequestDTO;
import com.milano.dto.request.OfferingRequestDTO;
import com.milano.dto.request.SalonRequestDTO;
import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.BookingResponseDTO;
import com.milano.dto.response.SalonReportResponseDTO;
import com.milano.entity.BOOKING_STATUS;
import com.milano.service.BookingService;
import com.milano.util.StandardResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@RestController
@RequestMapping("api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<StandardResponseDTO> createBooking(
            @RequestParam UUID salonId,
            @RequestBody @Valid BookingRequestDTO bookingRequestDTO) {

        // Temporary data (until Keycloak integration)
        //UUID customerId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        //UUID salonId = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");

        UserRequestDTO userRequestDTO =new UserRequestDTO();
        userRequestDTO.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));

        SalonRequestDTO salonRequestDTO = new SalonRequestDTO();
        salonRequestDTO.setId(salonId);
        salonRequestDTO.setOpenTime(LocalTime.now());
        salonRequestDTO.setCloseTime(LocalTime.now().plusHours(8));

        Set<OfferingRequestDTO> offeringRequestDTOS = new HashSet<>();

        OfferingRequestDTO offeringRequestDTO = new OfferingRequestDTO();
        offeringRequestDTO.setId(UUID.fromString("8c926c8d-6d0d-4239-80c9-7cbc86054283"));
        offeringRequestDTO.setPrice(890.00);
        offeringRequestDTO.setDuration(45);
        offeringRequestDTO.setName("Hair cut for men");

        offeringRequestDTOS.add(offeringRequestDTO);

        bookingService.createBooking(bookingRequestDTO,userRequestDTO,salonRequestDTO,offeringRequestDTOS);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(StandardResponseDTO.builder()
                        .code(201)
                        .message("Booking created successfully")
                        .data(null)
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
    public ResponseEntity<StandardResponseDTO> getBookingsByCustomer() {

        List<BookingResponseDTO> bookings =
                bookingService.getBookingsByCustomer(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Customer bookings retrieved successfully")
                        .data(bookings)
                        .build());
    }


    @GetMapping("/salon")
    public ResponseEntity<StandardResponseDTO> getBookingsBySalon() {

        List<BookingResponseDTO> bookings =
                bookingService.getBookingsBySalon(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));

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
    public ResponseEntity<StandardResponseDTO> getSalonReport() {

        SalonReportResponseDTO report =
                bookingService.getSalonReport(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StandardResponseDTO.builder()
                        .code(200)
                        .message("Salon report retrieved successfully")
                        .data(report)
                        .build());
    }
}