package com.booking.MovieBooking.Repository;

import com.booking.MovieBooking.Model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre,Long> {
    Optional<Theatre> findByIdAndActiveTrue(Long aLong);

    List<Theatre> findByCityAndActiveTrue(String city);
}
