package com.moroz.phonebookservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.moroz.phonebookservice.dao.UserDAO;
import com.moroz.phonebookservice.domain.PhoneNumber;
import com.moroz.phonebookservice.domain.User;
import com.moroz.phonebookservice.domain.Views;
import com.moroz.phonebookservice.exceptions.BadRequestException;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<User> getUser(@RequestParam(value="name", required = false, defaultValue="") String name) {
        if(name.isEmpty()) {
            return userDAO.getAllUsers();
        } else {
            return userDAO.getUserByName(name);
        }
    }

    @GetMapping("{id}")
    @JsonView(Views.Full.class)
    public User getUserById(@PathVariable Long id) {
        return userDAO.getUserById(id);
    }

    @GetMapping("{id}/phoneBook")
    public Map<PhoneNumber, String> getUserPhoneBook(@PathVariable Long id) {
        return userDAO.getUserPhoneBook(id);
    }

    @GetMapping("{id}/phoneBook/contacts")
    public List<PhoneNumber> getPhoneNumbersForContact(@PathVariable Long id,
                                                       @RequestParam(value="contactName") String contactName) {
        return userDAO.getPhoneNumbersForContact(id, contactName);
    }

    @GetMapping("{id}/phoneBook/phone")
    public Pair<PhoneNumber, String> getContactByPhoneNumber(@PathVariable Long id,
                                                             @RequestBody PhoneNumber phoneNumber) {
        return userDAO.getContactByPhoneNumber(id, phoneNumber);
    }

    @PostMapping
    public User addUser(@RequestParam(value="name") String name) {
        return userDAO.createUser(name);
    }

    @PostMapping("{id}/phoneBook")
    public Pair<PhoneNumber, String> addContactToUserPhoneBook(@PathVariable Long id,
                                                               @RequestParam(value="contactName") String contactName,
                                                               @RequestBody PhoneNumber phoneNumber) {
        return userDAO.addContactToUserPhoneBook(id, phoneNumber, contactName);
    }

    @PutMapping("{id}")
    public User renameUser(@PathVariable Long id,
                           @RequestParam(value="name") String name) {
        return userDAO.renameUser(id, name);
    }

    @PutMapping("{id}/phoneBook/phone")
    public Pair<PhoneNumber, String> updatePhoneNumber(@PathVariable Long id,
                                                       @RequestParam(value="newContactName", required = false, defaultValue="")
                                                               String newContactName,
                                                       @RequestBody PhoneNumber[] phoneNumbers) {
        if(phoneNumbers.length != 2) {
            throw new BadRequestException();
        }
        PhoneNumber currentPhoneNumber = phoneNumbers[0];
        PhoneNumber newPhoneNumber = phoneNumbers[1];
        return userDAO.updatePhoneNumber(id, currentPhoneNumber, newPhoneNumber, newContactName);
    }

    @PutMapping("{id}/phoneBook/contacts/{contactName}")
    public void renameContact(@PathVariable Long id,
                              @PathVariable String contactName,
                              @RequestParam(value="newContactName") String newContactName) {
        userDAO.renameContact(id, contactName, newContactName);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userDAO.deleteUser(id);
    }

    @DeleteMapping("{id}/phoneBook")
    public void deletePhoneNumberFromUserPhoneBook(@PathVariable Long id,
                                                   @RequestBody PhoneNumber phoneNumber) {
        userDAO.deletePhoneNumberFromUserPhoneBook(id, phoneNumber);
    }

    @DeleteMapping("{id}/phoneBook/contacts")
    public void deleteContactFromUserPhoneBook(@PathVariable Long id,
                                               @RequestParam(value="contactName") String contactName) {
        userDAO.deleteContactFromUserPhoneBook(id, contactName);
    }
}
