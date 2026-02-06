package com.booking.MovieBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TheatreShowsResponse {

    private Long theatreId;
    private String theatreName;
    private String city;
    private List<ShowInfoResponse> shows;

}
