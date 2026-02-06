package com.booking.MovieBooking.Repository;

import com.booking.MovieBooking.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Optional: find by email if needed
    User findByEmail(String email);
}
