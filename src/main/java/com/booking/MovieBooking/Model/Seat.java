package com.booking.MovieBooking.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Seat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber; // A1, A2
    private String type; // REGULAR / PREMIUM

    @ManyToOne
    @JsonIgnore
    private Screen screen;
}
