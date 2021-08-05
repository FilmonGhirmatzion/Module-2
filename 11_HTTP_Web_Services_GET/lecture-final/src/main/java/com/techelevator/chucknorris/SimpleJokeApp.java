package com.techelevator.chucknorris;

import org.springframework.web.client.RestTemplate;

public class SimpleJokeApp {

    public static void main(String[] args) {

        String apiUrl = "http://api.icndb.com/jokes/random?exclude=explicit&limitTo=nerdy";


        RestTemplate restTemplate = new RestTemplate();
        Response response = restTemplate.getForObject(apiUrl, Response.class);



        System.out.println(response.getValue().getJoke());
    }


}
