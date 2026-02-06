package com.booking.MovieBooking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MovieTheatreBrowseResponse {

    private Long movieId;
    private String movieName;
    private String language;
    private String genre;
    private List<TheatreShowsResponse> theatres;
}
