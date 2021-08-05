package com.techelevator.addressbook.services;

import com.techelevator.addressbook.model.Contact;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ContactsService {

    private String apiUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public ContactsService(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public List<Contact> getAllContacts() {
        Contact[] contacts = restTemplate.getForObject(apiUrl + "contacts", Contact[].class);
        return Arrays.asList(contacts);
    }

}
