package com.techelevator.addressbook.ServiceTest;

import com.techelevator.addressbook.module.Contact;
import com.techelevator.addressbook.services.ContactService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ContactServicesTest {

    private ContactService service;

    @Before
    public void setup(){
        service = new ContactService("http://localhost:8080/contacts");
    }

    @Test
    public void get_all_contacts() {
        List<Contact> contacts = service.getAllContacts();
        Assert.assertTrue(contacts.size()>0);

    }

}
