package com.moroz.phonebookservice.controller;

import com.moroz.phonebookservice.domain.PhoneNumber;
import com.moroz.phonebookservice.domain.User;
import com.moroz.phonebookservice.exceptions.PhoneNumberNotFoundException;
import com.moroz.phonebookservice.exceptions.UserNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerDeleteMethodsTest {

    @Autowired
    private UserController userController;

    @Test
    void deleteUser() {
        User user = userController.getUsers("").stream().findAny().get();
        userController.deleteUser(user.getId());

        try {
            userController.getUserById(user.getId());
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(UserNotFoundException.class, e.getClass());
        }
    }

    @Test
    void deletePhoneNumberFromUserPhoneBook() {
        User user = userController.getUsers("").stream().findAny().get();
        PhoneNumber phoneNumber = user.getPhoneBook().keySet().stream().findAny().get();
        userController.deletePhoneNumberFromUserPhoneBook(user.getId(), phoneNumber);

        try {
            userController.getContactByPhoneNumber(user.getId(), phoneNumber);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(PhoneNumberNotFoundException.class, e.getClass());
        }
    }

    @Test
    void deleteContactFromUserPhoneBook() {
        User user = userController.getUsers("").stream().findAny().get();
        String contactName = user.getPhoneBook().values().stream().findAny().get();
        userController.deleteContactFromUserPhoneBook(user.getId(), contactName);

        try {
            userController.getPhoneNumbersForContact(user.getId(), contactName);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals(PhoneNumberNotFoundException.class, e.getClass());
        }
    }
}