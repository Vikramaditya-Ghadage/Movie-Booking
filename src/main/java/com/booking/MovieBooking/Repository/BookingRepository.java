package com.booking.MovieBooking.Repository;

import com.booking.MovieBooking.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find all bookings for a specific show
    List<Booking> findByShowId(Long showId);
}
