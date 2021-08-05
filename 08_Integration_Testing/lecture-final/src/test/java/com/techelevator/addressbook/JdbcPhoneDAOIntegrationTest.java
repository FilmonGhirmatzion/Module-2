package com.techelevator.addressbook;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.SQLException;
import java.util.List;

public class JdbcPhoneDAOIntegrationTest {

    private static SingleConnectionDataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private PhoneDAO phoneDao;

    private long testContactId;

    /*
   BeforeClass runs ONCE before all the tests are run
   Setups and configures the dataSource
    */
    @BeforeClass
    public static void setupDataSource() {
        // Instantiate and configure the datasource
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/addressbook");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");
        // Set autoCommit = false to create the transaction scope
        dataSource.setAutoCommit(false);
    }

    /*
    AfterClass runs ONCE after all the tests are run
    Destroys the dataSource, which disconnects from the database
     */
    @AfterClass
    public static void destroyDataSource() {
        dataSource.destroy();
    }

    /*
    After runs after each individual test method is run
    Rollback the transaction
     */
    @After
    public void rollbackTransaction() throws SQLException {
        dataSource.getConnection().rollback();
    }

    @Before
    public void setup() {
        phoneDao = new JdbcPhoneDAO(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "INSERT INTO contact (first_name, last_name) VALUES ( 'test','test') RETURNING contact_id";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql);
        row.next();
        testContactId = row.getLong("contact_id");
    }

    @Test
    public void retrieve_phone_by_contact() {
        // Arrange
        // 1) create a phone
        //       - require a contact_phone row
        //          -- contact_phone requires a contact
        Phone newPhone = new Phone();
        newPhone.setPhoneNumber("1-555-555-5555");
        newPhone.setType("Mobile");

        phoneDao.add(newPhone, testContactId);

        // Act
        List<Phone> contactPhones = phoneDao.getByContact(testContactId);

        // Assert
        Assert.assertNotNull("Phone list was null", contactPhones);
        Assert.assertEquals("Phone list was not 1", 1, contactPhones.size());
        Phone returnedPhone = contactPhones.get(0);
        assertPhonesEqual(newPhone, returnedPhone);
    }

    /*
        Can write our own assert methods or can override .equals in the data object and use assertEquals()
     */
    private void assertPhonesEqual(Phone expected, Phone actual) {
        Assert.assertEquals("Phone Id not equal", expected.getPhoneId(), actual.getPhoneId());
        Assert.assertEquals("Phone number not equal", expected.getPhoneNumber(), actual.getPhoneNumber());
        Assert.assertEquals("Phone type not equal", expected.getType(), actual.getType());
        Assert.assertEquals("Phone type description not equal", expected.getTypeDescription(), actual.getTypeDescription());
    }
}
