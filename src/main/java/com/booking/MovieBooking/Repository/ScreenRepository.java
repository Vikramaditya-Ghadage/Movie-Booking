package com.booking.MovieBooking.Repository;

import com.booking.MovieBooking.Model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepository extends JpaRepository<Screen,Long> {
}
