package com.techelevator.addressbook;

public interface EmailDAO {

    Email get(long emailId);
    Email getByContactId(long contactId);
    void add(Email email);
    void update(Email email);
    void delete(long emailId);
}
