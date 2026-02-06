package com.booking.MovieBooking.Controller;

import com.booking.MovieBooking.Model.Screen;
import com.booking.MovieBooking.Service.ScreenService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/screen")
public class ScreenController {

    @Autowired
    private ScreenService screenService;

    @PostMapping("/{theatreId}")
    public ResponseEntity<Screen> addScreen(@PathVariable Long theatreId, @RequestBody Screen screen) {
        return new ResponseEntity<>(screenService.addScreen(theatreId, screen), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Screen> getScrren(@PathParam("scrrenid") Long scrrenid)
    {
        return new ResponseEntity<>(screenService.getScreen(scrrenid),HttpStatus.OK);
    }
}

