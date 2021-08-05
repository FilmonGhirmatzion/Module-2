package com.techelevator.dao;

import com.techelevator.dao.addressbook.Contact;
import com.techelevator.dao.addressbook.ContactDAO;
import com.techelevator.dao.addressbook.JdbcContactDAO;
import org.apache.commons.dbcp2.BasicDataSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ContactApp {

    public static void main(String[] args) {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/addressbook");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        ContactDAO contactDao = new JdbcContactDAO(dataSource);

        Contact newContact = new Contact();
        newContact.setFirstName("John");
        newContact.setLastName("Fulton");
        newContact.setDateAdded( LocalDate.now() );

        // Create the contact (INSERT)
        contactDao.create( newContact );

        // Save updates to the contact (UPDATE)
        Contact updatedContact = new Contact();
        updatedContact.setContactId( newContact.getContactId() );
        updatedContact.setFirstName("Updated");
        updatedContact.setLastName("AlsoUpdated");

        contactDao.save( updatedContact );

        // SELECTs all the contacts
        List<Contact> contacts = contactDao.list();

        for (Contact contact: contacts) {
            System.out.println(contact.getFirstName() + " "
                    + contact.getLastName());
        }

        Scanner input = new Scanner(System.in);

        System.out.print("Id to select then delete >>");
        int id = input.nextInt();
        input.nextLine();

        Contact selectedContact = contactDao.getById( id );

        System.out.println(selectedContact.getFirstName()
                + " " + selectedContact.getLastName());

        contactDao.delete(id);

    }
}
