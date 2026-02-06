package com.booking.MovieBooking.Repository;

import com.booking.MovieBooking.Model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findByShowIdAndSeatSeatNumberIn(Long showId, List<String> seatNumbers);

    List<ShowSeat> findByShowIdAndBookedFalse(Long showId);
}
