package com.booking.MovieBooking.Controller;


import com.booking.MovieBooking.Model.Theatre;
import com.booking.MovieBooking.Service.TheatreService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatre")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @PostMapping
    public ResponseEntity<Theatre> addTheatre(@RequestBody Theatre theatre)
    {
        return new ResponseEntity<>(theatreService.addTheatre(theatre),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Theatre>> findAllTheatresByCity(@PathParam("city") String city)
    {
        return new ResponseEntity<>(theatreService.findAllTheatresByCity(city),HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Theatre> getTheatreById(@PathVariable long id)
    {
        return new ResponseEntity<>(theatreService.findTheatreById(id), HttpStatus.OK);
    }

}
