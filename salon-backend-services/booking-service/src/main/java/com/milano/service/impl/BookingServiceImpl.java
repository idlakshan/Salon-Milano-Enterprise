package com.milano.service.impl;

import com.milano.dto.request.BookingRequestDTO;
import com.milano.dto.request.OfferingRequestDTO;
import com.milano.dto.request.SalonRequestDTO;
import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.BookingResponseDTO;
import com.milano.dto.response.SalonReportResponseDTO;
import com.milano.entity.BOOKING_STATUS;
import com.milano.entity.Booking;
import com.milano.exception.EntryNotFoundException;
import com.milano.exception.ValidationException;
import com.milano.repo.BookingRepo;
import com.milano.service.BookingService;
import com.milano.util.BookingMapper;
import lombok.RequiredArgsConstructor;
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

    @Override
    public void createBooking(BookingRequestDTO bookingRequestDTO, UserRequestDTO userRequestDTO,
                              SalonRequestDTO salonRequestDTO, Set<OfferingRequestDTO> offeringRequestDTOS) {

        //calc total duration
        int totalDuration = offeringRequestDTOS.stream()
                .mapToInt(OfferingRequestDTO::getDuration)
                .sum();

        LocalDateTime bookingStartTime = bookingRequestDTO.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        if (!isTimeSlotAvailable(salonRequestDTO, bookingStartTime, bookingEndTime)) {
            throw new ValidationException("The requested time slot is already taken or invalid.");
        }

        double totalPrice = offeringRequestDTOS.stream()
                .mapToDouble(OfferingRequestDTO::getPrice)
                .sum();

        Set<UUID> idList = offeringRequestDTOS.stream()
                .map(OfferingRequestDTO::getId)
                .collect(Collectors.toSet());

        bookingRepo.save(bookingMapper.toBooking(bookingRequestDTO,
                userRequestDTO, salonRequestDTO, totalPrice, idList, bookingEndTime));
    }


    //check booking time
    public Boolean isTimeSlotAvailable(SalonRequestDTO salon,
                                       LocalDateTime bookingStartTime,
                                       LocalDateTime bookingEndTime) {

        List<BookingResponseDTO> existingBookings = getBookingsBySalon(salon.getId());

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
    public List<BookingResponseDTO> getBookingsByCustomer(UUID customerId) {

        List<Booking> bookings = bookingRepo.findByCustomerId(customerId);
        return bookings.stream()
                .map(bookingMapper::toBookingResponseDTO)
                .toList();
    }

    @Override
    public List<BookingResponseDTO> getBookingsBySalon(UUID salonId) {

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
                .orElseThrow(() ->
                        new EntryNotFoundException("Booking not found by provided id"));
        booking.setStatus(status);
        bookingRepo.save(booking);
    }

    @Override
    public List<BookingResponseDTO> getBookingsByDate(LocalDate date, UUID salonId) {
        List<Booking> bookings  = bookingRepo.findBySalonId(salonId);

        if(date==null){
            return bookings.stream()
                    .map(bookingMapper::toBookingResponseDTO)
                    .toList();
        }
        return bookings.stream()
                .filter(booking ->
                        isSameDate(booking.getStartTime(), date) || isSameDate(booking.getEndTime(), date))
                .map(bookingMapper::toBookingResponseDTO)
                .toList();
    }

    public boolean isSameDate(LocalDateTime dateTime, LocalDate date){
          return dateTime.toLocalDate().isEqual(date);
    }

    @Override
    public SalonReportResponseDTO getSalonReport(UUID salonId) {

        List<BookingResponseDTO> bookingsBySalon = getBookingsBySalon(salonId);

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


        return bookingMapper.toSalonReportResponseDTO(salonId, totalEarnings, totalBookings,
                cancelledBookings.size(),
                totalRefund);
    }
}
