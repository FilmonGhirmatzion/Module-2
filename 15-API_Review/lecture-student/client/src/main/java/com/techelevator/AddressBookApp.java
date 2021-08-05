package com.techelevator;

import com.techelevator.addressbook.module.Contact;
import com.techelevator.addressbook.services.ConsoleService;
import com.techelevator.addressbook.services.ContactService;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AddressBookApp {

    private static final String API_URL = "http://localhost:8080/";

    private ConsoleService consoleService = new ConsoleService();
    private ContactService contactService = new ContactService(API_URL);

    public void run() {
        while (true) {
            String userChoice = consoleService.mainMenu();
            if (userChoice.equalsIgnoreCase("1")) {
                showAlContacts();
                 // show all contacts

            }else if(userChoice.equalsIgnoreCase("Q")){

                break;
            }
        }


    }
    private void showAlContacts(){
        try {
            List<Contact> contacts = contactService.getAllContacts();
            ConsoleService.showContacts(contacts);
        }catch (ResourceAccessException e){
            consoleService.errorCannotConnect();
        }catch (RestClientResponseException e){
            consoleService.errorClientExeption(e.getRawStatusCode(), e.getStatusText());

        }


    }

    public static void main(String[] args) {
        new AddressBookApp().run();
    }
}
