package com.booking.MovieBooking.Repository;

import com.booking.MovieBooking.Model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByMovieIdAndScreenTheatreCityAndShowTimeBetween(
            Long movieId, String city, LocalDateTime start, LocalDateTime end);


    @Query("""
    SELECT s FROM Show s JOIN FETCH s.movie m
    JOIN FETCH s.screen sc JOIN FETCH sc.theatre t WHERE t.id = :theatreId AND s.showDate = :date
    ORDER BY s.showTime
    """)
    List<Show> findShowsByTheatreAndDate(Long theatreId, LocalDate date);



    @Query("""
    SELECT s FROM Show s
    JOIN FETCH s.movie m
    JOIN FETCH s.screen sc
    JOIN FETCH sc.theatre t
    WHERE m.id = :movieId
      AND LOWER(t.city) = LOWER(:city)
      AND LOWER(m.language) = LOWER(:language)
      AND LOWER(m.genre) = LOWER(:genre)
      AND s.showDate = :date
      AND t.active = true
    """)
    List<Show> findShowsForMovieBrowse(Long movieId,
                                           String city,
                                           String language,
                                           String genre,
                                           LocalDate date);


    @Query("""
    SELECT s FROM Show s
    JOIN FETCH s.movie m
    JOIN FETCH s.screen sc
    JOIN FETCH sc.theatre t
    WHERE LOWER(t.city) = LOWER(:city)
      AND s.showDate = :date
      AND t.active = true
    """)
        List<Show> findShowsByCityAndDate(String city, LocalDate date);

}
