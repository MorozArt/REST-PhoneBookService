package com.moroz.phonebookservice.controller;

import com.moroz.phonebookservice.domain.PhoneNumber;
import com.moroz.phonebookservice.domain.User;
import com.moroz.phonebookservice.exceptions.PhoneNumberNotFoundException;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerPutMethodsTest {

    @Autowired
    private UserController userController;

    @Test
    void renameUser() {
        User user = userController.getUsers("").stream().findAny().get();
        String oldUserName = user.getName();
        String newUserName = "New User Name";

        userController.renameUser(user.getId(), newUserName);

        Assert.assertEquals(newUserName, userController.getUserById(user.getId()).getName());
        Assert.assertNotEquals(oldUserName, userController.getUserById(user.getId()).getName());
    }

    @Test
    void updatePhoneNumber() {
        User user = userController.getUsers("").stream().findAny().get();
        PhoneNumber oldPhoneNumber = user.getPhoneBook().keySet().stream().findAny().get();
        String oldContactName = user.getPhoneBook().get(oldPhoneNumber);
        PhoneNumber newPhoneNumber = new PhoneNumber(1, 222, 3333333);
        String newContactName = "New Contact";
        PhoneNumber[] phoneNumbers = new PhoneNumber[2];
        phoneNumbers[0] = oldPhoneNumber;
        phoneNumbers[1] = newPhoneNumber;

        Pair<PhoneNumber, String> updateContact = userController.updatePhoneNumber(user.getId(), newContactName, phoneNumbers);

        Assert.assertEquals(newPhoneNumber, updateContact.getKey());
        Assert.assertEquals(newContactName, updateContact.getValue());
        Assert.assertNotEquals(oldPhoneNumber, updateContact.getKey());
        Assert.assertNotEquals(oldContactName, updateContact.getKey());
    }

    @Test
    void renameContact() {
        User user = userController.getUsers("").stream().findAny().get();
        String oldContactName = user.getPhoneBook().values().stream().findAny().get();
        String newContactName = "New Contact";
        userController.renameContact(user.getId(), oldContactName, newContactName);

        try {
            userController.getPhoneNumbersForContact(user.getId(), oldContactName);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(PhoneNumberNotFoundException.class, e.getClass());
        }

        Assert.assertFalse(userController.getPhoneNumbersForContact(user.getId(), newContactName).isEmpty());
    }
}