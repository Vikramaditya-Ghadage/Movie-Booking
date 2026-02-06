package com.booking.MovieBooking.Service;

import com.booking.MovieBooking.Dto.MovieTheatreBrowseResponse;
import com.booking.MovieBooking.Dto.ShowInfoResponse;
import com.booking.MovieBooking.Dto.ShowResponse;
import com.booking.MovieBooking.Dto.TheatreShowsResponse;
import com.booking.MovieBooking.Exception.ResourceNotFoundException;
import com.booking.MovieBooking.Model.*;
import com.booking.MovieBooking.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    public Show createShow(Long movieId, Long screenId, Show show) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        show.setMovie(movie);
        show.setScreen(screen);
        Show savedShow = showRepository.save(show);

        // 🎟 Create ShowSeats for this show
        List<Seat> seats = seatRepository.findByScreenId(screenId);
        List<ShowSeat> showSeats = seats.stream().map(seat -> {
            ShowSeat ss = new ShowSeat();
            ss.setShow(savedShow);
            ss.setSeat(seat);
            ss.setBooked(false);
            return ss;
        }).toList();

        showSeatRepository.saveAll(showSeats);

        return savedShow;
    }


    public Show updateShow(Long showId, Show updatedShow) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        show.setShowDate(updatedShow.getShowDate());
        show.setShowTime(updatedShow.getShowTime());
        show.setBasePrice(updatedShow.getBasePrice());

        return showRepository.save(show);
    }

    public void deleteShow(Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        showRepository.delete(show);
    }

    public List<ShowResponse> getShowsByTheatreAndDate(Long theatreId, LocalDate date) {

        List<Show> shows = showRepository.findShowsByTheatreAndDate(theatreId, date);

        return shows.stream()
                .map(s -> new ShowResponse(
                        s.getId(),
                        s.getMovie().getTitle(),
                        s.getScreen().getTheatre().getName(),
                        s.getScreen().getName(),
                        s.getShowDate(),
                        s.getShowTime(),
                        s.getBasePrice()
                ))
                .toList();
    }



    public MovieTheatreBrowseResponse browseMovieByTheatres(
            Long movieId, String city, String language, String genre, LocalDate date) {

        List<Show> shows = showRepository.findShowsForMovieBrowse(movieId, city, language, genre, date);

        if (shows.isEmpty()) {
            throw new ResourceNotFoundException("No shows found for selected filters");
        }

        Movie movie = shows.get(0).getMovie();

        Map<Long, TheatreShowsResponse> theatreMap = new HashMap<>();

        for (Show show : shows) {
            Theatre theatre = show.getScreen().getTheatre();

            theatreMap.putIfAbsent(theatre.getId(),
                    new TheatreShowsResponse(
                            theatre.getId(),
                            theatre.getName(),
                            theatre.getCity(),
                            new ArrayList<>()
                    )
            );

            theatreMap.get(theatre.getId()).getShows().add(
                    new ShowInfoResponse(
                            show.getId(),
                            show.getShowDate(),
                            show.getShowTime(),
                            show.getScreen().getName(),
                            show.getBasePrice()
                    )
            );
        }

        MovieTheatreBrowseResponse response = new MovieTheatreBrowseResponse();
        response.setMovieId(movie.getId());
        response.setMovieName(movie.getTitle());
        response.setLanguage(movie.getLanguage());
        response.setGenre(movie.getGenre());
        response.setTheatres(new ArrayList<>(theatreMap.values()));

        return response;
    }



    public List<MovieTheatreBrowseResponse> browseMoviesByCityAndDate(String city, LocalDate date) {

        List<Show> shows = showRepository.findShowsByCityAndDate(city, date);

        if (shows.isEmpty()) {
            throw new ResourceNotFoundException("No shows found for selected city and date");
        }

        Map<Long, MovieTheatreBrowseResponse> movieMap = new HashMap<>();

        for (Show show : shows) {
            Movie movie = show.getMovie();
            Theatre theatre = show.getScreen().getTheatre();

            movieMap.putIfAbsent(movie.getId(),
                    new MovieTheatreBrowseResponse(
                            movie.getId(),
                            movie.getTitle(),
                            movie.getLanguage(),
                            movie.getGenre(),
                            new ArrayList<>()
                    )
            );

            MovieTheatreBrowseResponse movieResponse = movieMap.get(movie.getId());

            TheatreShowsResponse theatreResponse = movieResponse.getTheatres()
                    .stream()
                    .filter(t -> t.getTheatreId().equals(theatre.getId()))
                    .findFirst()
                    .orElseGet(() -> {
                        TheatreShowsResponse newTheatre = new TheatreShowsResponse(
                                theatre.getId(),
                                theatre.getName(),
                                theatre.getCity(),
                                new ArrayList<>()
                        );
                        movieResponse.getTheatres().add(newTheatre);
                        return newTheatre;
                    });

            theatreResponse.getShows().add(
                    new ShowInfoResponse(
                            show.getId(),
                            show.getShowDate(),
                            show.getShowTime(),
                            show.getScreen().getName(),
                            show.getBasePrice()
                    )
            );
        }

        return new ArrayList<>(movieMap.values());
    }

}