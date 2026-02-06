package com.booking.MovieBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponseDto {
    private Long showId;
    private String movieTitle;
    private String theatreName;
    private String screenName;
    private LocalDate showDate;
    private LocalTime showTime;
    private Double price;
    private List<SeatResponse> seats; // booked or available
}
