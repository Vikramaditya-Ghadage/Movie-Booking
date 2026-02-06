package com.booking.MovieBooking.Service;

import com.booking.MovieBooking.Dto.*;
import com.booking.MovieBooking.Model.*;
import com.booking.MovieBooking.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired private ShowRepository showRepository;
    @Autowired private ShowSeatRepository showSeatRepository;
    @Autowired private BookingRepository bookingRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private DiscountService discountService;

    @Transactional
    public BookingResponseDto bookTickets(BookingRequest request, Long userId) {

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find seats by seat numbers
        List<ShowSeat> seats = showSeatRepository
                .findByShowIdAndSeatSeatNumberIn(show.getId(), request.getSeatNumbers());

        if (seats.size() != request.getSeatNumbers().size()) {
            throw new RuntimeException("Some seats not found");
        }

        // Check already booked
        for (ShowSeat ss : seats) {
            if (ss.isBooked()) {
                throw new RuntimeException("Seat already booked: " + ss.getSeat().getSeatNumber());
            }
        }

        int ticketCount = seats.size();

        // Apply discounts (can implement your 50% third ticket / 20% afternoon show here)
        double finalAmount = discountService.calculateFinalAmount(show, ticketCount);

        // Mark seats booked
        seats.forEach(ss -> ss.setBooked(true));
        showSeatRepository.saveAll(seats);

        // Save booking
        Booking booking = new Booking();
        booking.setShow(show);
        booking.setUser(user);
        booking.setTotalSeats(ticketCount);
        booking.setTotalAmount(finalAmount);
        booking.setBookingTime(LocalDateTime.now());
        Booking savedBooking = bookingRepository.save(booking);

        // Map booked seats
        List<SeatResponse> bookedSeatDtos = seats.stream()
                .map(ss -> new SeatResponse(ss.getSeat().getSeatNumber(), ss.getSeat().getType()))
                .collect(Collectors.toList());

        // Prepare Show DTO
        ShowResponseDto showDto = new ShowResponseDto(
                show.getId(),
                show.getMovie().getTitle(),
                show.getScreen().getTheatre().getName(),
                show.getScreen().getName(),
                show.getShowDate(),
                show.getShowTime(),
                show.getBasePrice(),
                bookedSeatDtos
        );

        return new BookingResponseDto(
                savedBooking.getId(),
                savedBooking.getBookingTime(),
                ticketCount,
                finalAmount,
                showDto,
                bookedSeatDtos
        );
    }

    // Available seats
    public List<SeatResponse> getAvailableSeats(Long showId) {
        List<ShowSeat> availableSeats = showSeatRepository.findByShowIdAndBookedFalse(showId);
        return availableSeats.stream()
                .map(ss -> new SeatResponse(ss.getSeat().getSeatNumber(), ss.getSeat().getType()))
                .collect(Collectors.toList());
    }

    // All bookings for a show
    public List<BookingResponseDto> getBookingsByShow(Long showId) {
        List<Booking> bookings = bookingRepository.findByShowId(showId);
        return bookings.stream().map(booking -> {
            Show show = booking.getShow();
            List<SeatResponse> bookedSeats = booking.getShow().getShowSeats().stream()
                    .filter(ShowSeat::isBooked)
                    .map(ss -> new SeatResponse(ss.getSeat().getSeatNumber(), ss.getSeat().getType()))
                    .collect(Collectors.toList());

            ShowResponseDto showDto = new ShowResponseDto(
                    show.getId(),
                    show.getMovie().getTitle(),
                    show.getScreen().getTheatre().getName(),
                    show.getScreen().getName(),
                    show.getShowDate(),
                    show.getShowTime(),
                    show.getBasePrice(),
                    bookedSeats
            );

            return new BookingResponseDto(
                    booking.getId(),
                    booking.getBookingTime(),
                    booking.getTotalSeats(),
                    booking.getTotalAmount(),
                    showDto,
                    bookedSeats
            );
        }).collect(Collectors.toList());
    }
}
