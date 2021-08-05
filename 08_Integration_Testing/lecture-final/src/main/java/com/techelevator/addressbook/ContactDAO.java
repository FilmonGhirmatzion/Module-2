package com.techelevator.addressbook;

import java.util.List;

public interface ContactDAO {

    // Get a list of all contacts
    List<Contact> list();

    // Get a single contact by the contact id
    Contact getById(long id);

    // Create a new contact
    void create(Contact contact);

    // Save changes to an existing contact (update it)
    void save(Contact contact);

    // delete a contact
    void delete(long id);
}
