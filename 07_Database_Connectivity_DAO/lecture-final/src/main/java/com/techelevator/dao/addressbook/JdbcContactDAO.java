package com.techelevator.dao.addressbook;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcContactDAO implements ContactDAO {

    // Create an instance variable for the JdbcTemplate
    private JdbcTemplate jdbcTemplate;

    public JdbcContactDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Contact> list() {
        String sql = "SELECT contact_id, first_name, last_name, date_added FROM contact";

        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);

        List<Contact> contacts = new ArrayList<Contact>();
        while (rows.next()) {
            Contact contact = mapRowToContact( rows );
            contacts.add( contact );
        }
        return contacts;
    }

    @Override
    public Contact getById(long id) {
        String sql = "SELECT contact_id, first_name, last_name, date_added " +
                "FROM contact WHERE contact_id = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, id);

        Contact contact = null;
        if (rows.next()) {
            contact = mapRowToContact(rows);
        }

        return contact;
    }

    @Override
    public void create(Contact contact) {
        String sql = "INSERT INTO contact (contact_id, first_name, last_name, date_added) " +
                "VALUES (DEFAULT, ?, ?, ?) RETURNING contact_id";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, contact.getFirstName(), contact.getLastName(), contact.getDateAdded());
        rows.next();
        contact.setContactId( rows.getLong("contact_id") );
    }

    @Override
    public void save(Contact contact) {
        String sql = "UPDATE contact SET first_name = ?, last_name = ? WHERE contact_id = ?";
        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getContactId());
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM contact WHERE contact_id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Contact mapRowToContact(SqlRowSet row) {
        Contact contact = new Contact();

        contact.setContactId( row.getLong("contact_id") );
        contact.setFirstName( row.getString("first_name") );
        contact.setLastName( row.getString("last_name") );

        if (row.getDate("date_added") != null) {
            contact.setDateAdded(row.getDate("date_added").toLocalDate());
        }

        return contact;
    }
}
