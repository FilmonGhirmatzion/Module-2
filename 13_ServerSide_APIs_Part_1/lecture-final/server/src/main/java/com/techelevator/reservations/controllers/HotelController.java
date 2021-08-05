package com.techelevator.reservations.controllers;

import com.techelevator.reservations.dao.HotelDAO;
import com.techelevator.reservations.dao.MemoryHotelDAO;
import com.techelevator.reservations.dao.MemoryReservationDAO;
import com.techelevator.reservations.dao.ReservationDAO;
import com.techelevator.reservations.models.Hotel;
import com.techelevator.reservations.models.Reservation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HotelController {

    private HotelDAO hotelDAO;
    private ReservationDAO reservationDAO;

    public HotelController() {
        this.hotelDAO = new MemoryHotelDAO();
        this.reservationDAO = new MemoryReservationDAO(hotelDAO);
    }

    /**
     * Return all hotels, optionally filtered by city and state
     * @return
     *
     * http://localhost:8080/hotels?state=Ohio
     */
    @RequestMapping(path = "/hotels", method = RequestMethod.GET)
    public List<Hotel> list(@RequestParam(required=false, defaultValue = "") String state,
                            @RequestParam(required=false) String city,
                            @RequestParam(required=false, defaultValue = "0") int population) {

        List<Hotel> hotels = hotelDAO.list();

        if (state.length() == 0 && city == null) {
            return hotels;
        }

        List<Hotel> filteredHotels = new ArrayList<Hotel>();
        for (Hotel hotel : hotels) {
            if (city != null) {
                if (hotel.getAddress().getCity().equalsIgnoreCase(city)) {
                    filteredHotels.add(hotel);
                }
            } else {
                if (state != null) {
                    if (hotel.getAddress().getState().equalsIgnoreCase(state)) {
                        filteredHotels.add(hotel);
                    }
                }
            }

        }

        return filteredHotels;
    }


    /**
     * Return All Hotels
     *
     * @return a list of all hotels in the system
     */
//    @RequestMapping(path = "/hotels", method = RequestMethod.GET)
//    public List<Hotel> list() {
//        return hotelDAO.list();
//    }


    /**
     * Return hotel information
     *
     * @param id the id of the hotel
     * @return all info for a given hotel
     */
    @RequestMapping(path = "/hotels/{id}", method = RequestMethod.GET)
    public Hotel get(@PathVariable int id) {
        return hotelDAO.get(id);
    }

    @RequestMapping(path = "/reservations", method = RequestMethod.GET)
    public List<Reservation> getReservation() {
        List<Reservation> reservations = reservationDAO.findAll();
        return reservations;
    }

    @RequestMapping(path="/reservations/{id}", method=RequestMethod.GET)
    public Reservation getReservation(@PathVariable int id) {
        return reservationDAO.get(id);
    }

    //http://localhost:8080/hotels/1/reservations - GET
    @RequestMapping(path="/hotels/{hotelId}/reservations", method=RequestMethod.GET)
    public List<Reservation> getReservationsForHotel( @PathVariable int hotelId) {
        return reservationDAO.findByHotel(hotelId);
    }

    // Add a new Hotel
    // http://localhost:8080/hotels/1/reservations
    @RequestMapping(path="/hotels/{hotelId}/reservations", method=RequestMethod.POST)
    public Reservation addReservation(@RequestBody Reservation reservation, @PathVariable int hotelId) {
        return reservationDAO.create(reservation, hotelId);
    }
}
