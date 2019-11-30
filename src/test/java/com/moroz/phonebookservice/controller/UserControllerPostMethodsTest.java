package com.moroz.phonebookservice.controller;

import com.moroz.phonebookservice.domain.PhoneNumber;
import com.moroz.phonebookservice.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserControllerPostMethodsTest {

    @Autowired
    private UserController userController;

    @Test
    void addUser() {
        String newUserName = "New Test User";
        Assert.assertTrue(userController.addUser(newUserName));

        Assert.assertEquals(newUserName, userController.getUsers(newUserName).get(0).getName());
    }

    @Test
    void addContactToUserPhoneBook() {
        User user = userController.getUsers("").stream().findAny().get();
        String newContactName = "new contact";
        PhoneNumber newContactPhoneNumber = new PhoneNumber(7, 999, 1234567);

        Assert.assertTrue(userController.addContactToUserPhoneBook(user.getId(), newContactName, newContactPhoneNumber));

        Assert.assertEquals(newContactName,
                userController.getContactByPhoneNumber(user.getId(), newContactPhoneNumber).getValue());
    }
}