package com.booking.MovieBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ShowResponse {
    private Long showId;
    private String movieTitle;
    private String theatreName;
    private String screenName;
    private LocalDate showDate;
    private LocalTime showTime;
    private Double price;
}

