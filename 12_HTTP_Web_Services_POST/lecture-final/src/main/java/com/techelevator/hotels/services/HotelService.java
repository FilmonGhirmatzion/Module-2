package com.techelevator.hotels.services;

import com.techelevator.hotels.models.Hotel;
import com.techelevator.hotels.models.Reservation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

public class HotelService {

  private final String BASE_URL;
  private final RestTemplate restTemplate = new RestTemplate();
  private final ConsoleService console = new ConsoleService();

  public HotelService(String url) {
    BASE_URL = url;
  }

  /**
   * Create a new reservation in the hotel reservation system
   *
   * @param newReservation
   * @return Reservation
   */
  public Reservation addReservation(String newReservation) {
    // need to build the reservation object - this is just
    // for this app and has nothing to do with how to do a POST
    Reservation reservation = makeReservation(newReservation);
    if (reservation == null) {
      return null;
    }

    // 1. Create a header for the Content-Type application/json
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    // 2. Put the reservation in the message body
                                                                // ( objectForTheMessageBody, headers)
    HttpEntity<Reservation> entity = new HttpEntity<Reservation>(reservation, headers);

    try {
      // 3. Send the POST with the header and the message body
      // ( url, httpEntityWithTheHeaderAndBody, classToDeserializeTo)
      reservation = restTemplate.postForObject(BASE_URL + "reservations", entity, Reservation.class);
    } catch (ResourceAccessException e) {
      console.printError(e.getMessage());
    } catch (RestClientResponseException e) {
      console.printError(e.getRawStatusCode() + " " + e.getStatusText());
    }
    return reservation;
  }

  /**
   * Updates an existing reservation by replacing the old one with a new
   * reservation
   *
   * @param updatedReservation
   * @return
   */
  public Reservation updateReservation(String updatedReservation) {
    Reservation reservation = makeReservation(updatedReservation);
    if (reservation == null) {
      return null;
    }

    // 1. Create the Header
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    // 2. Create the Message Body
    HttpEntity<Reservation> entity = new HttpEntity<Reservation>(reservation, headers);
    // 3. PUT using the header and message body

    String endpointUrl = BASE_URL + "reservations/" + reservation.getId();

    try {
      restTemplate.put(endpointUrl, entity);
    } catch (ResourceAccessException e) {
      console.printError(e.getMessage());
    } catch (RestClientResponseException e) {
      console.printError(e.getRawStatusCode() + " " + e.getStatusText());
    }


    return reservation;
  }

  /**
   * Delete an existing reservation
   *
   * @param id
   */
  public void deleteReservation(int id) {
    String endpointUrl = BASE_URL + "reservations/" + id;
    try {
      restTemplate.delete(endpointUrl);
    } catch (ResourceAccessException e) {
      console.printError(e.getMessage());
    } catch (RestClientResponseException e) {
      console.printError(e.getRawStatusCode() + " " + e.getStatusText());
    }

  }

  /**
   * List all hotels in the system
   *
   * @return
   */
  public Hotel[] listHotels() {
    Hotel[] hotels = null;
    try {
      String endpoint = BASE_URL + "hotels";
      hotels = restTemplate.getForObject(endpoint, Hotel[].class);
    } catch (ResourceAccessException e) {
       /* ResourceAccessException is thrown when the client cannot
          connect to the server
        */
      // handle connection error
      console.printError( e.getMessage() );

    } catch (RestClientResponseException e) {
      /*  RestClientResponseException is thrown when the server returns an error status code
          like 4xx (400, 404, etc) or a 5xx (500)
       */
      // handle server response errors
      // rawStatusCode contains the HTTP Status Code (404)
      // statusText contains the HTTP status message (Not Found)
      console.printError(e.getRawStatusCode() + " " + e.getStatusText());
    }

    return hotels;
  }

  /**
   * Get the details for a single hotel by id
   *
   * @param id
   * @return Hotel
   */
  public Hotel getHotel(int id) {
    Hotel hotel = null;
    String endpoint = BASE_URL + "hotels/" + id;
    try {
      hotel = restTemplate.getForObject(endpoint, Hotel.class);
    } catch (ResourceAccessException e) {
      console.printError(e.getMessage());
    } catch (RestClientResponseException e) {
      if (e.getRawStatusCode() == 404) {
        console.printError("The endpoint was not found");
      } else {
        console.printError(e.getRawStatusCode() + " " + e.getStatusText());
      }
    }
    return hotel;
  }


  

    /* DON'T MODIFY ANY METHODS BELOW */
  /**
   * List all reservations in the hotel reservation system
   *
   * @return Reservation[]
   */
  public Reservation[] listReservations() {
    Reservation[] reservations = null;
    try {
      reservations = restTemplate.getForObject(BASE_URL + "reservations", Reservation[].class);
    } catch (RestClientResponseException ex) {
      console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
    } catch (ResourceAccessException ex) {
      console.printError(ex.getMessage());
    }
    return reservations;
  }

  /**
   * List all reservations by hotel id.
   *
   * @param hotelId
   * @return Reservation[]
   */
  public Reservation[] listReservationsByHotel(int hotelId) {
    Reservation[] reservations = null;
    try {
      reservations = restTemplate.getForObject(BASE_URL + "hotels/" + hotelId + "/reservations", Reservation[].class);
    } catch (RestClientResponseException ex) {
      console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
    } catch (ResourceAccessException ex) {
      console.printError(ex.getMessage());
    }
    return reservations;
  }

  /**
   * Find a single reservation by the reservationId
   *
   * @param reservationId
   * @return Reservation
   */
  public Reservation getReservation(int reservationId) {
    Reservation reservation = null;
    try {
      reservation = restTemplate.getForObject(BASE_URL + "reservations/" + reservationId, Reservation.class);
    } catch (RestClientResponseException ex) {
      console.printError(ex.getRawStatusCode() + " : " + ex.getStatusText());
    } catch (ResourceAccessException ex) {
      console.printError(ex.getMessage());
    }
    return reservation;
  }

  private Reservation makeReservation(String CSV) {
    String[] parsed = CSV.split(",");

    // invalid input (
    if (parsed.length < 5 || parsed.length > 6) {
      return null;
    }

    // Add method does not include an id and only has 5 strings
    if (parsed.length == 5) {
      // Create a string version of the id and place into an array to be concatenated
      String[] withId = new String[6];
      String[] idArray = new String[] { new Random().nextInt(1000) + "" };
      // place the id into the first position of the data array
      System.arraycopy(idArray, 0, withId, 0, 1);
      System.arraycopy(parsed, 0, withId, 1, 5);
      parsed = withId;
    }

    return new Reservation(Integer.parseInt(parsed[0].trim()), Integer.parseInt(parsed[1].trim()), parsed[2].trim(),
        parsed[3].trim(), parsed[4].trim(), Integer.parseInt(parsed[5].trim()));
  }

}
