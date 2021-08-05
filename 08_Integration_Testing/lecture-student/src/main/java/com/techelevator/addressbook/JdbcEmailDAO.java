package com.techelevator.addressbook;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;

public class JdbcEmailDAO implements EmailDAO {

    private JdbcTemplate jdbcTemplate;

    public JdbcEmailDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Email get(long emailId) {
        Email email = null;
        String sql = "SELECT email_id, contact_id, email_address, type, type_description FROM email WHERE email_id = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, emailId);
        if (rows.next()) {
            email = mapRowToEmail(rows);
        }
        return email;
    }

    @Override
    public Email getByContactId(long contactId) {
        Email email = null;
        String sql = "SELECT email_id, contact_id, email_address, type, type_description FROM email WHERE contact_id = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, contactId);
        if (rows.next()) {
            email = mapRowToEmail(rows);
        }
        return email;
    }

    @Override
    public void add(Email email) {
        String sql = "INSERT INTO email (email_id, contact_id, email_address, type, type_description) " +
                "VALUES (DEFAULT, ?, ?, ?, ?) RETURNING email_id";
        Long emailId = jdbcTemplate.queryForObject(sql, Long.class, email.getContactId(),
                email.getEmailAddress(), email.getType(), email.getTypeDescription());
        email.setEmailId(emailId);
    }

    @Override
    public void update(Email email) {
        String sql = "UPDATE email SET email_address = '', type = '', type_description = '' WHERE email_id = ?";
        jdbcTemplate.update(sql, email.getEmailAddress(), email.getType(),
                email.getTypeDescription(), email.getEmailId());
    }

    @Override
    public void delete(long emailId) {
        String sql = "DELETE FROM email WHERE email_id = ?";
        jdbcTemplate.update(sql, emailId);
    }

    private Email mapRowToEmail(SqlRowSet row) {
        Email email = new Email();

        email.setEmailId( row.getLong("email_id") );
        email.setContactId( row.getLong("contact_id") );
        email.setEmailAddress( row.getString("email_address") );
        email.setType( row.getString("type") );
        email.setTypeDescription( row.getString("type_description") );

        return email;
    }
}
