package com.techelevator.loginexample;

import com.techelevator.hotels.models.Hotel;
import com.techelevator.hotels.models.Reservation;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


public class LoginExample {

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        String username = "user";
        String password = "password";
        String url = "http://localhost:8080/";
        String token = "";

        /*
          LOGIN AND GET THE TOKEN
         */
        Credentials credentials = new Credentials();
        credentials.setUsername(username);
        credentials.setPassword(password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Credentials> loginRequestEntity = new HttpEntity<Credentials>(credentials, headers);

        ResponseEntity<Map> loginResponseEntity = restTemplate.exchange(url + "login", HttpMethod.POST,
                loginRequestEntity, Map.class);
        token = (String) loginResponseEntity.getBody().get("token");

        System.out.println(token);

        /*
          USING THE TOKEN WITH A REQUEST WITH NO MESSAGE BODY  (GET/DELETE)
         */

        HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.setBearerAuth(token);
        HttpEntity getRequestEntity = new HttpEntity<>(getHeaders);

        Hotel hotel = restTemplate.exchange(url + "/hotels/1", HttpMethod.GET,
                getRequestEntity, Hotel.class).getBody();   // with exchange must call getBody() to deserialize the object

        System.out.println(hotel);



        /*
         USING THE TOKEN WITH REQUEST WITH A MESSAGE BODY  (POST/PUT)
         */
        Reservation reservation = new Reservation();
        reservation.setHotelID(1);
        reservation.setFullName("test");
        reservation.setCheckinDate("10/12/2021");
        reservation.setCheckoutDate("10/14/2021");
        reservation.setGuests(4);

        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);
        postHeaders.setBearerAuth(token);
        HttpEntity<Reservation> postRequestEntity = new HttpEntity<Reservation>(reservation, postHeaders);

        Reservation returnedReservation = restTemplate.exchange(url + "/hotels/1/reservations", HttpMethod.POST,
                postRequestEntity, Reservation.class).getBody();

        System.out.println(returnedReservation);




    }
}
