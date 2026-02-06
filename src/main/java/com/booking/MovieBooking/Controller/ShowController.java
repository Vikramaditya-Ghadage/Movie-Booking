package com.booking.MovieBooking.Controller;

import com.booking.MovieBooking.Dto.MovieTheatreBrowseResponse;
import com.booking.MovieBooking.Dto.ShowResponse;
import com.booking.MovieBooking.Exception.ResourceNotFoundException;
import com.booking.MovieBooking.Model.Show;
import com.booking.MovieBooking.Service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/movie/{movieId}/screen/{screenId}")
    public ResponseEntity<Show> createShow(
            @PathVariable Long movieId,
            @PathVariable Long screenId,
            @RequestBody Show show) {
        return ResponseEntity.ok(showService.createShow(movieId, screenId, show));
    }

    @PutMapping("/{showId}")
    public ResponseEntity<Show> updateShow(
            @PathVariable Long showId,
            @RequestBody Show updatedShow) {

        return ResponseEntity.ok(showService.updateShow(showId, updatedShow));
    }

    @DeleteMapping("/{showId}")
    public ResponseEntity<String> deleteShow(@PathVariable Long showId) {
        showService.deleteShow(showId);
        return ResponseEntity.ok("Show deleted successfully");
    }

    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<List<ShowResponse>> getShowsByTheatreAndDate(
            @PathVariable Long theatreId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(showService.getShowsByTheatreAndDate(theatreId, date));
    }




    @GetMapping("/browse/{movieId}")
    public ResponseEntity<MovieTheatreBrowseResponse> browseMovieByTheatres(
            @PathVariable Long movieId,
            @RequestParam String city,
            @RequestParam String language,
            @RequestParam String genre,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(
                showService.browseMovieByTheatres(movieId, city, language, genre, date)
        );
    }


    @GetMapping("/browse")
    public ResponseEntity<List<MovieTheatreBrowseResponse>> browseMoviesByCityAndDate(
            @RequestParam String city,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(showService.browseMoviesByCityAndDate(city, date));
    }
}