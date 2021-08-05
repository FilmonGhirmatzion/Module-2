package com.techelevator.addressbook.services;

import com.techelevator.addressbook.module.Contact;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactService {
    private String apiUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public ContactService(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public List<Contact> getAllContacts() {

        Contact[] contacts = restTemplate.getForObject(apiUrl + "/contacts", Contact[].class);

        return Arrays.asList(contacts);
    }

}
