package com.techelevator.addressbook.model.dao;

import com.techelevator.addressbook.model.Contact;
import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class JdbcContactDAOIntegrationTest {

    private static SingleConnectionDataSource dataSource;
    private ContactDAO contactDAO;
    private JdbcTemplate jdbcTemplate;

    @BeforeClass
    public static void setupDataSource() {
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/addressbook");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        dataSource.setAutoCommit(false);
    }

    @AfterClass
    public static void destroyConnection() {
        dataSource.destroy();
    }

    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }

    @Before
    public void setup() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        contactDAO = new JdbcContactDAO(jdbcTemplate);
    }

    @Test
    public void get_list_of_all_contacts() {
        List<Contact> originalContacts = contactDAO.list();
        addContact( new Contact(0, "testFirst", "testLast", LocalDate.now()));
        List<Contact> newContacts = contactDAO.list();
        Assert.assertEquals(originalContacts.size() + 1, newContacts.size());
    }

    private void addContact(Contact contact) {
        String sql = "INSERT INTO contact (contact_id, first_name, last_name, date_added) VALUES (DEFAULT, ?, ?, current_timestamp)";
        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName());
    }
}
