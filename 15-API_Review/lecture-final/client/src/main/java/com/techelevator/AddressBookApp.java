package com.techelevator;

import com.techelevator.addressbook.model.Contact;
import com.techelevator.addressbook.services.ConsoleService;
import com.techelevator.addressbook.services.ContactsService;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

public class AddressBookApp {

    private static final String API_URL = "http://localhost:8080/";

    private ConsoleService consoleService = new ConsoleService();
    private ContactsService contactsService = new ContactsService(API_URL);

    public void run() {

        while(true) {

            String userChoice = consoleService.mainMenu();

            if (userChoice.equalsIgnoreCase("1")) {
                showAllContacts();
            } else if (userChoice.equalsIgnoreCase("Q")) {
                break;
            }

        }


    }

    private void showAllContacts() {
        try {
            List<Contact> contacts = contactsService.getAllContacts();
            consoleService.showContacts(contacts);
        } catch (ResourceAccessException e) {
            consoleService.errorCannotConnect();
        } catch (RestClientResponseException e) {
            consoleService.errorClientException(e.getRawStatusCode(), e.getStatusText());
        }

    }



    public static void main(String[] args) {
        new AddressBookApp().run();
    }
}
