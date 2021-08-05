package com.techelevator.dao.addressbook;

import java.util.List;

public interface ContactDAO {

    List<Contact> list();

    Contact getById(long id);


     void create (Contact contact);


     void save (Contact contact);

     void  delete (long id);






}
