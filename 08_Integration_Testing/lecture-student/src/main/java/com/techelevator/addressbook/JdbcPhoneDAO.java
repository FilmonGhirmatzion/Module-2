package com.techelevator.addressbook;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcPhoneDAO implements PhoneDAO {

    private JdbcTemplate jdbcTemplate;

    public JdbcPhoneDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Phone> getByContact(long contactId) {

        List<Phone> phones = new ArrayList<Phone>();

        String sql = "SELECT phone.phone_id, phone_number, type, type_description " +
                "FROM phone JOIN contact_phone ON phone.phone_id = contact_phone.phone_id " +
                "WHERE contact_phone.contact_id = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, contactId);
        while (rows.next()) {
            phones.add( mapRowToPhone(rows) );
        }
        return phones;
    }

    @Override
    public void add(Phone phone, long contactId) {

        String sql = "INSERT INTO phone (phone_id, phone_number, type, type_description) " +
                "VALUES (DEFAULT, ?, ?, ?) RETURNING phone_id";

        Long phoneId = jdbcTemplate.queryForObject(sql, Long.class, phone.getPhoneNumber(),
                phone.getType(), phone.getTypeDescription());
        phone.setPhoneId(phoneId);

        insertContactPhone(phoneId, contactId);

    }



    @Override
    public void update(Phone phone) {
        String sql = "UPDATE phone SET phone_number = '', type = '', type_description = '' WHERE phone_id = ?";
        jdbcTemplate.update(sql, phone.getPhoneNumber(), phone.getType(), phone.getTypeDescription(), phone.getPhoneId());
    }

    @Override
    public void delete(long phoneId) {
        deleteContactPhone(phoneId);
        String sql = "DELETE FROM phone WHERE phone_id = ?";
        jdbcTemplate.update(sql, phoneId);
    }

    private void deleteContactPhone(long phoneId) {
        String sql = "DELETE FROM contact_phone WHERE phone_id = ?";
        jdbcTemplate.update(sql, phoneId);
    }

    private void insertContactPhone(long phoneId, long contactId) {
        String sql = "INSERT INTO contact_phone (phone_id, contact_id) VALUES (?,?)";
        jdbcTemplate.update(sql, phoneId, contactId);
    }

    private Phone mapRowToPhone(SqlRowSet row) {
        Phone phone = new Phone();
        phone.setPhoneId( row.getLong("phone_id") );
        phone.setPhoneNumber( row.getString("phone_number") );
        phone.setType( row.getString("type") );
        phone.setTypeDescription( row.getString("type_description"));

        return phone;
    }
}
