package com.techelevator.addressbook.controller;

import com.techelevator.addressbook.model.Contact;
import com.techelevator.addressbook.model.dao.ContactDAO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContactController {

    private ContactDAO contactDAO;

    public ContactController(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    @RequestMapping(path="/contacts", method= RequestMethod.GET)
    public List<Contact> list() {
        return contactDAO.list();

    }

}
