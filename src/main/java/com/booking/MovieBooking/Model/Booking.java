package com.booking.MovieBooking.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Show show;

    @ManyToOne
    private User user; // added user reference

    private int totalSeats;
    private double totalAmount;

    private LocalDateTime bookingTime = LocalDateTime.now();
}
