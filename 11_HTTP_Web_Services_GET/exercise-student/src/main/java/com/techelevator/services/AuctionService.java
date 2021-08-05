package com.techelevator.services;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import com.techelevator.models.Auction;


public class AuctionService {
    public static final String BASE_URL = "http://localhost:3000/auctions";
    public RestTemplate restTemplate = new RestTemplate();
    private final ConsoleService console = new ConsoleService();

    public Auction[] listAllAuctions() {
        Auction[] auctions = restTemplate.getForObject(BASE_URL, Auction[].class);
        return auctions;
    }

    public Auction listDetailsForAuction(int id) {
        String endPoint = BASE_URL + "/" + id;
        Auction auction = restTemplate.getForObject(endPoint, Auction.class);
        return auction;
    }

    public Auction[] findAuctionsSearchTitle(String title) {
        // api code here
        String endPoint = BASE_URL + "?title_like=" + title;
        Auction[] auctions = restTemplate.getForObject(endPoint, Auction[].class);
        return auctions;
    }

    public Auction[] findAuctionsSearchPrice(double price) {
        // api code here
        String endPoint = BASE_URL + "?currentBid_lte=" + price;
        Auction[] auctions = restTemplate.getForObject(endPoint, Auction[].class);
        return auctions;
    }
}