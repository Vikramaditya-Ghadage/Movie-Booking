package com.booking.MovieBooking.Service;

import com.booking.MovieBooking.Exception.ResourceNotFoundException;
import com.booking.MovieBooking.Model.Theatre;
import com.booking.MovieBooking.Repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;

    public Theatre addTheatre(Theatre theatre) {
        theatre.setActive(true);
        return theatreRepository.save(theatre);
    }

    public List<Theatre> findAllTheatresByCity(String city) {
        List<Theatre> theatreList=theatreRepository.findByCityAndActiveTrue(city);
        if(theatreList.isEmpty())
            throw new ResourceNotFoundException("No Theatres available in city:"+city);

        return theatreList;
    }

    public Theatre findTheatreById(Long id)
    {
        return theatreRepository.findByIdAndActiveTrue(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("Resource Not Found with id:"+id));
    }

}
