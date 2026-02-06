package com.booking.MovieBooking.Controller;

import com.booking.MovieBooking.Dto.*;
import com.booking.MovieBooking.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Book tickets with userId
    @PostMapping("/user/{userId}")
    public ResponseEntity<BookingResponseDto> bookTickets(
            @PathVariable Long userId,
            @RequestBody BookingRequest request) {

        BookingResponseDto response = bookingService.bookTickets(request, userId);
        return ResponseEntity.ok(response);
    }

    // Get available seats for a show
    @GetMapping("/show/{showId}/available-seats")
    public ResponseEntity<List<SeatResponse>> getAvailableSeats(@PathVariable Long showId) {
        List<SeatResponse> seats = bookingService.getAvailableSeats(showId);
        return ResponseEntity.ok(seats);
    }

    // Get all bookings for a show
    @GetMapping("/show/{showId}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsForShow(@PathVariable Long showId) {
        List<BookingResponseDto> bookings = bookingService.getBookingsByShow(showId);
        return ResponseEntity.ok(bookings);
    }
}
