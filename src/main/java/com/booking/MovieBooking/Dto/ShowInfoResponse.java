package com.booking.MovieBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowInfoResponse {

    private Long showId;
    private LocalDate showDate;
    private LocalTime showTime;
    private String screenName;
    private double price;
}