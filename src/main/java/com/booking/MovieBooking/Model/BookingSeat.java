package com.booking.MovieBooking.Model;

import jakarta.persistence.*;

@Entity
public class BookingSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Booking booking;

    @ManyToOne
    private ShowSeat showSeat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public ShowSeat getShowSeat() {
        return showSeat;
    }

    public void setShowSeat(ShowSeat showSeat) {
        this.showSeat = showSeat;
    }

}
