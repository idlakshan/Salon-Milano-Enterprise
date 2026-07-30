package com.milano.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.milano.dto.OfferingDTO;
import com.milano.dto.SalonDTO;
import com.milano.dto.UserDTO;
import com.milano.dto.request.BookingRequestDTO;
import com.milano.dto.response.BookingResponseDTO;
import com.milano.dto.PaymentDTO;
import com.milano.dto.response.SalonReportResponseDTO;
import com.milano.entity.BOOKING_STATUS;
import com.milano.entity.Booking;
import com.milano.entity.PAYMENT_METHOD;
import com.milano.exception.EntryNotFoundException;
import com.milano.exception.ValidationException;
import com.milano.repo.BookingRepo;
import com.milano.service.BookingService;
import com.milano.service.client.PaymentFeignClient;
import com.milano.service.client.SalonFeignClient;
import com.milano.service.client.ServiceOfferingFeignClient;
import com.milano.service.client.UserFeignClient;
import com.milano.util.BookingMapper;
import com.milano.util.StandardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final BookingMapper bookingMapper;
    private final SalonFeignClient salonFeignClient;
    private final UserFeignClient userFeignClient;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;
    private final PaymentFeignClient paymentFeignClient;
    private final ObjectMapper objectMapper;

    @Override
    public PaymentDTO createBooking(BookingRequestDTO bookingRequestDTO, String jwt, UUID salonId,
                                    PAYMENT_METHOD paymentMethod) {

        ResponseEntity<StandardResponseDTO> userResponse = userFeignClient.getUserProfile(jwt);
        UserDTO userDTO = objectMapper.convertValue(userResponse.getBody().getData(), UserDTO.class);

        ResponseEntity<StandardResponseDTO> salonResponse = salonFeignClient.findSalonById(salonId);
        SalonDTO salonDTO = objectMapper.convertValue(salonResponse.getBody().getData(), SalonDTO.class);

        Set<UUID> serviceIds = bookingRequestDTO.getServicesIds();
        ResponseEntity<StandardResponseDTO> servicesResponse = serviceOfferingFeignClient
                .getServicesByIds(serviceIds);

        List<?> rawList = (List<?>) servicesResponse.getBody().getData();
        Set<OfferingDTO> offeringDTOs = rawList.stream()
                .map(item -> objectMapper.convertValue(item, OfferingDTO.class))
                .collect(Collectors.toSet());

        if (offeringDTOs.isEmpty()) {
            throw new ValidationException("No valid services found for the provided service IDs.");
        }

        // calc total duration
        int totalDuration = offeringDTOs.stream()
                .mapToInt(OfferingDTO::getDuration)
                .sum();

        LocalDateTime bookingStartTime = bookingRequestDTO.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        if (!isTimeSlotAvailable(salonDTO, bookingStartTime, bookingEndTime)) {
            throw new ValidationException("The requested time slot is already taken or invalid.");
        }

        double totalPrice = offeringDTOs.stream()
                .mapToDouble(OfferingDTO::getPrice)
                .sum();

        Set<UUID> idList = offeringDTOs.stream()
                .map(OfferingDTO::getId)
                .collect(Collectors.toSet());

        Booking savedBooking = bookingRepo.save(bookingMapper.toBooking(
                bookingRequestDTO,
                userDTO,
                salonDTO,
                totalPrice,
                idList,
                bookingEndTime
        ));

        BookingResponseDTO bookingDTO = bookingMapper.toBookingResponseDTO(savedBooking);

        ResponseEntity<StandardResponseDTO> paymentResponse = paymentFeignClient.createPaymentLink(bookingDTO, paymentMethod, jwt);
        return objectMapper.convertValue(paymentResponse.getBody().getData(), PaymentDTO.class);
    }

    // check booking time
    public Boolean isTimeSlotAvailable(SalonDTO salon,
                                       LocalDateTime bookingStartTime,
                                       LocalDateTime bookingEndTime) {

        List<BookingResponseDTO> existingBookings = getBookingsBySalonId(salon.getId());

        LocalDateTime salonOpenTime = salon.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salon.getCloseTime().atDate(bookingStartTime.toLocalDate());

        if (bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime)) {
            return false;
        }

        for (BookingResponseDTO existingBooking : existingBookings) {
            LocalDateTime existingStartTime = existingBooking.getStartTime();
            LocalDateTime existingEndTime = existingBooking.getEndTime();

            if ((bookingStartTime.isBefore(existingEndTime) && bookingEndTime.isAfter(existingStartTime)) ||
                    bookingStartTime.isEqual(existingStartTime) || bookingEndTime.isEqual(existingEndTime)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<BookingResponseDTO> getBookingsByCustomer(String jwt) {

        ResponseEntity<StandardResponseDTO> userResponse = userFeignClient.getUserProfile(jwt);
        UserDTO userDTO = objectMapper.convertValue(userResponse.getBody().getData(), UserDTO.class);

        List<Booking> bookings = bookingRepo.findByCustomerId(userDTO.getId());
        return bookings.stream()
                .map(bookingMapper::toBookingResponseDTO)
                .toList();
    }


    @Override
    public List<BookingResponseDTO> getBookingsBySalon(String jwt) {
        ResponseEntity<StandardResponseDTO> salon = salonFeignClient.findSalonByOwnerId(jwt);
        SalonDTO salonDTO = objectMapper.convertValue(salon.getBody().getData(), SalonDTO.class);

        return getBookingsBySalonId(salonDTO.getId());
    }


    public List<BookingResponseDTO> getBookingsBySalonId(UUID salonId) {
        List<Booking> bySalonId = bookingRepo.findBySalonId(salonId);
        return bySalonId.stream()
                .map(bookingMapper::toBookingResponseDTO)
                .toList();
    }

    @Override
    public BookingResponseDTO getBookingsById(UUID id) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new EntryNotFoundException("Booking not found"));
        return bookingMapper.toBookingResponseDTO(booking);
    }

    @Override
    public void updateBooking(UUID bookingId, BOOKING_STATUS status) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new EntryNotFoundException("Booking not found by provided id"));
        booking.setStatus(status);
        bookingRepo.save(booking);
    }

    @Override
    public List<BookingResponseDTO> getBookingsByDate(LocalDate date, UUID salonId) {
        List<Booking> bookings = bookingRepo.findBySalonId(salonId);

        if (date == null) {
            return bookings.stream()
                    .map(bookingMapper::toBookingResponseDTO)
                    .toList();
        }
        return bookings.stream()
                .filter(booking -> isSameDate(booking.getStartTime(), date) || isSameDate(booking.getEndTime(), date))
                .map(bookingMapper::toBookingResponseDTO)
                .toList();
    }

    public boolean isSameDate(LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().isEqual(date);
    }

    @Override
    public SalonReportResponseDTO getSalonReport(String jwt) {

        ResponseEntity<StandardResponseDTO> salon = salonFeignClient.findSalonByOwnerId(jwt);
        SalonDTO salonDTO = objectMapper.convertValue(salon.getBody().getData(), SalonDTO.class);


        List<BookingResponseDTO> bookingsBySalon = getBookingsBySalonId(salonDTO.getId());

        double totalEarnings = bookingsBySalon.stream()
                .filter(booking -> booking.getStatus() != BOOKING_STATUS.CANCEL)
                .mapToDouble(BookingResponseDTO::getTotalPrice)
                .sum();

        int totalBookings = bookingsBySalon.size();

        List<BookingResponseDTO> cancelledBookings = bookingsBySalon.stream()
                .filter(booking -> booking.getStatus() == BOOKING_STATUS.CANCEL)
                .toList();

        double totalRefund = cancelledBookings.stream()
                .mapToDouble(BookingResponseDTO::getTotalPrice)
                .sum();

        return bookingMapper.toSalonReportResponseDTO(salonDTO.getId(), totalEarnings, totalBookings,
                cancelledBookings.size(),
                totalRefund);
    }
}