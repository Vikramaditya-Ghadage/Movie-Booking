package com.booking.MovieBooking.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Show show;

    @ManyToOne
    private Seat seat;

    private boolean booked = false;
}

