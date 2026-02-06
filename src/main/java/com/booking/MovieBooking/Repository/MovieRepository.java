package com.booking.MovieBooking.Repository;

import com.booking.MovieBooking.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
