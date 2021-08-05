package com.techelevator.addressbook.services;

import com.techelevator.addressbook.model.Contact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ContactsServiceTest {

    private ContactsService service;

    @Before
    public void setup() {
        service = new ContactsService("http://localhost:8080/");
    }

    @Test
    public void get_all_contacts() {
        List<Contact> contacts = service.getAllContacts();
        Assert.assertTrue(contacts.size() > 0);
    }

}
