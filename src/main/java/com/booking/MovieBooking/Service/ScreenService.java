package com.booking.MovieBooking.Service;

import com.booking.MovieBooking.Exception.ResourceNotFoundException;
import com.booking.MovieBooking.Model.Screen;
import com.booking.MovieBooking.Model.Seat;
import com.booking.MovieBooking.Model.Theatre;
import com.booking.MovieBooking.Repository.ScreenRepository;
import com.booking.MovieBooking.Repository.SeatRepository;
import com.booking.MovieBooking.Repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired private SeatRepository seatRepository;

    public Screen addScreen(Long theatreId, Screen screen) {

        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        screen.setTheatre(theatre);
        Screen savedScreen = screenRepository.save(screen);

        generateSeats(savedScreen);

        return savedScreen;
    }

    private void generateSeats(Screen screen) {

        int seatsPerRow = 10;
        int totalSeats = screen.getTotalSeats();
        List<Seat> seats = new ArrayList<>();

        for (int i = 0; i < totalSeats; i++) {
            char row = (char) ('A' + (i / seatsPerRow));
            int number = (i % seatsPerRow) + 1;

            Seat seat = new Seat();
            seat.setSeatNumber(row + "" + number);
            seat.setType(row < 'C' ? "PREMIUM" : "REGULAR");
            seat.setScreen(screen);

            seats.add(seat);
        }

        seatRepository.saveAll(seats);
        System.out.println("Generated " + seats.size() + " seats for screen: " + screen.getName());
    }



    public Screen getScreen(Long id)
    {
        return screenRepository
                .findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Screen Not found with id:"+id));
    }

    public Screen updateScreen(Long screenId, Screen updated) {
        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        screen.setName(updated.getName());
        screen.setTotalSeats(updated.getTotalSeats());

        return screenRepository.save(screen);
    }

}
