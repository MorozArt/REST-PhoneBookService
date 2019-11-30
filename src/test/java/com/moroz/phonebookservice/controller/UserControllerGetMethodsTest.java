package com.moroz.phonebookservice.controller;

import com.moroz.phonebookservice.dao.UserDAO;
import com.moroz.phonebookservice.domain.PhoneNumber;
import com.moroz.phonebookservice.domain.User;
import com.moroz.phonebookservice.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
class UserControllerGetMethodsTest {

    @Autowired
    private UserController userController;

    @Test
    void getUsers() {
        Assert.assertEquals(2, userController.getUsers("").size());

        User user = userController.getUsers("").stream().findAny().get();
        Assert.assertFalse(userController.getUsers(user.getName()).isEmpty());

        Assert.assertTrue(userController.getUsers("Is not a User!").isEmpty());
    }

    @Test
    void getUserById() {
        List<User> users = userController.getUsers("");
        for(User user : users) {
            Assert.assertEquals(user, userController.getUserById(user.getId()));
        }

        try {
            userController.getUserById(-1L);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(UserNotFoundException.class, e.getClass());
        }
    }

    @Test
    void getUserPhoneBook() {
        User user = userController.getUsers("").stream().findAny().get();
        Assert.assertNotNull(userController.getUserPhoneBook(user.getId()));
    }

    @Test
    void getPhoneNumbersForContact() {
        User user = userController.getUsers("").stream().findAny().get();
        String anyContactNameFromUserPhoneBook = user.getPhoneBook().values().stream().findAny().get();
        Assert.assertFalse(userController.getPhoneNumbersForContact(user.getId(), anyContactNameFromUserPhoneBook).isEmpty());
    }

    @Test
    void getContactByPhoneNumber() {
        User user = userController.getUsers("").stream().findAny().get();
        PhoneNumber phoneNumber = user.getPhoneBook().keySet().stream().findAny().get();
        Assert.assertEquals(user.getPhoneBook().get(phoneNumber),
                userController.getContactByPhoneNumber(user.getId(), phoneNumber).getValue());
    }
}