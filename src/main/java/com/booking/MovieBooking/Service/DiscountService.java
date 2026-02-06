package com.booking.MovieBooking.Service;

import com.booking.MovieBooking.Model.Show;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class DiscountService {

    public double calculateFinalAmount(Show show, int ticketCount) {

        double basePrice = show.getBasePrice();
        double totalPrice = basePrice * ticketCount;
        double discount = 0;

        String city = show.getScreen().getTheatre().getCity();
        String theatre = show.getScreen().getTheatre().getName();

        if (!isOfferCityAndTheatre(city, theatre)) {
            return totalPrice;
        }

        // 50% off every 3rd ticket
        int thirdTicketCount = ticketCount / 3;
        discount += thirdTicketCount * (basePrice * 0.5);

        // 20% off afternoon show
        if (isAfternoonShow(show.getShowTime())) {
            discount += totalPrice * 0.20;
        }

        return totalPrice - discount;
    }

    private boolean isAfternoonShow(LocalTime time) {
        return !time.isBefore(LocalTime.NOON) && time.isBefore(LocalTime.of(16, 0));
    }

    private boolean isOfferCityAndTheatre(String city, String theatre) {
        List<String> cities = List.of("Pune", "Mumbai", "Delhi");
        List<String> theatres = List.of("PVR", "INOX", "Cinepolis");

        return cities.contains(city) &&
                theatres.stream().anyMatch(theatre::contains);
    }
}
