package com.techelevator.addressbook.services;


import com.techelevator.addressbook.module.Contact;

import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private static final Scanner in = new Scanner(System.in);
    ;
 public void errorCannotConnect(){
     System.out.println("Cannot connect to server!");
 }

 public void errorClientExeption (int statusCode, String message ){
     System.out.println(statusCode + "" + message);
 }

 public String mainMenu() {
        System.out.println("1) Show All Contact");
        System.out.println("Q) Quit");
        return in.nextLine();
    }

    public static void showContacts(List<Contact> contacts) {
        for (int i = 0; i < contacts.size(); i++) {
            System.out.print((i + 1) + ") ");
            System.out.print(contacts.get(i).getContactId() + " ");
            System.out.print(contacts.get(i).getFirstName() + " ");
            System.out.print(contacts.get(i).getLastName() + " ");
            System.out.println(contacts.get(i).getDateAdded());

        }
    }
}