package com.techelevator.addressbook;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class JdbcContactDAOIntegrationTest {

    private static SingleConnectionDataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private ContactDAO contactDao;

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

    /*
    Before runs before each individual test method is run
    Instantiate the object being tested (JdbcDao) and insert any shared test data that is needed
     */
    @Before
    public void setupBeforeTest() {
        contactDao = new JdbcContactDAO(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*
    TESTING SELECT for single object
     */
    @Test
    public void retrieve_contact_by_contact_id() {
        // Arrange - create a contact and store it, then insert it into the database
        Contact contact = getContact("testFirstName", "testLastName");

        contactDao.create( contact );

        // Act
        Contact contactFromDatabase = contactDao.getById( contact.getContactId() );

        // Assert
        Assert.assertNotNull("Contact was null",contactFromDatabase);
        Assert.assertEquals("Contacts not equal", contact, contactFromDatabase);

    }

    /*
    SELECT with multiple objects being returned
     */
    @Test
    public void retrieve_multiple_contacts() {
        // Arrange
        // Get a count of the number of values in the table
        List<Contact> originalList = contactDao.list();

        //insert multiple contacts
        Contact contactOne = getContact("firstOne", "lastOne");
        Contact contactTwo = getContact("firstTwo", "lastTwo");
        contactDao.create( contactOne );
        contactDao.create( contactTwo );

        // Act
        List<Contact> contactsFromDatabase = contactDao.list();

        // Assert
        Assert.assertEquals(originalList.size() + 2, contactsFromDatabase.size());
    }

    @Test
    public void retrieve_multiple_contacts_with_truncate() {
        // Arrange
        // Clear the table of all data
        clearContactTable();

        //insert multiple contacts
        Contact contactOne = getContact("firstOne", "lastOne");
        Contact contactTwo = getContact("firstTwo", "lastTwo");
        contactDao.create( contactOne );
        contactDao.create( contactTwo );

        // Act
        List<Contact> contactsFromDatabase = contactDao.list();

        // Assert
        Assert.assertEquals(2, contactsFromDatabase.size());
    }

    /*
    INSERT
     */
    @Test
    public void insert_contact() {
        // Arrange
        Contact newContact = getContact("testFirst", "testLast");

        // Act
        contactDao.create(newContact);

        // Assert
        Assert.assertTrue(newContact.getContactId() > 0);

        // retrieve the contact from the database
        Contact contactFromDatabase = contactDao.getById(newContact.getContactId());

        Assert.assertEquals(newContact, contactFromDatabase);
    }

    /*
    UPDATE
    Examples do not use methods from the dao
     */
    @Test
    public void update_contact() {
        // Arrange - Create and insert a new contact
        Contact newContact = getContact("firstName", "lastName");
        insertNewTestContact(newContact);

        // Act - change the contact and call the update method
        newContact.setFirstName("updatedFirstName");
        newContact.setLastName("updatedLastName");
        contactDao.save(newContact);

        // Assert - retrieve the contact by id and compare against the local contact we stored
        Contact contactFromDatabase = retrieveContactById( newContact.getContactId() );
        Assert.assertNotNull("Contact not found", contactFromDatabase);
        Assert.assertEquals("Contacts not equal", newContact, contactFromDatabase);
    }

    /*
    DELETE
     */
    @Test
    public void delete_contact() {
        // Arrange - create and insert a new contact
        Contact newContact = getContact("firstName", "lastName");
        insertNewTestContact(newContact);

        // Act - call the delete method on the DAO
        contactDao.delete( newContact.getContactId() );

        // Assert - verify that the contact is no longer in the database
        Assert.assertNull( retrieveContactById(newContact.getContactId()) );
    }


    private Contact retrieveContactById(long contactId) {
        Contact contact = null;

        String sql = "SELECT contact_id, first_name, last_name, date_added FROM contact " +
                "WHERE contact_id = ?";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, contactId);

        if (row.next()) {
            contact = new Contact();
            contact.setContactId(row.getLong("contact_id"));
            contact.setFirstName(row.getString("first_name"));
            contact.setLastName(row.getString("last_name"));
            contact.setDateAdded(row.getDate("date_added").toLocalDate());

        }

        return contact;
    }

    private void insertNewTestContact(Contact newContact) {
        String sql = "INSERT INTO contact (contact_id, first_name, last_name, date_added) " +
                "VALUES (DEFAULT, ?, ?, ?) RETURNING contact_id";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, newContact.getFirstName(), newContact.getLastName(),
                newContact.getDateAdded());
        row.next();
        newContact.setContactId(row.getLong("contact_id"));
    }

    private void clearContactTable() {
        String sql = "TRUNCATE TABLE contact CASCADE";
        jdbcTemplate.update(sql);
    }
    private Contact getContact(String firstName, String lastName) {
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setDateAdded(LocalDate.now());
        return contact;
    }

}
