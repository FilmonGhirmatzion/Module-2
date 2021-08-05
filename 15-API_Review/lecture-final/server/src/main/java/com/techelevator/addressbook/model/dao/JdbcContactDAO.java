package com.techelevator.addressbook.model.dao;

import com.techelevator.addressbook.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcContactDAO implements ContactDAO {

    private JdbcTemplate jdbcTemplate;

    public JdbcContactDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Contact> list() {
        String sql = "SELECT contact_id, first_name, last_name, date_added FROM contact";
        List<Contact> contacts = new ArrayList<Contact>();
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);

        while (rows.next()) {
            contacts.add(mapRowToContact(rows));
        }
        return contacts;
    }


    private Contact  mapRowToContact(SqlRowSet rows) {
        Contact contact = new Contact();
        contact.setContactId( rows.getLong("contact_id"));
        contact.setFirstName( rows.getString("first_name"));
        contact.setLastName( rows.getString("last_name"));

        if (rows.getDate("date_added") != null) {
            contact.setDateAdded(rows.getDate("date_added").toLocalDate());
        }
        return contact;
    }
}
