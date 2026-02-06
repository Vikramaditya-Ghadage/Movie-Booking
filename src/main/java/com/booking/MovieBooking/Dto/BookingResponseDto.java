package com.booking.MovieBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {
    private Long bookingId;
    private LocalDateTime bookingTime;
    private int totalSeats;
    private double totalAmount;
    private ShowResponseDto show;
    private List<SeatResponse> bookedSeats;
}
