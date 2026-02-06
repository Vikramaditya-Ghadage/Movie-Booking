package com.booking.MovieBooking.Dto;

import lombok.Data;
import java.util.List;

@Data
public class BookingRequest {
    private Long showId;
    private List<String> seatNumbers;
}
