package com.techelevator.addressbook;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public interface PhoneDAO {

    List<Phone> getByContact(long contactId);
    void add(Phone phone, long contactId);
    void update(Phone phone);
    void delete(long phoneId);
}
