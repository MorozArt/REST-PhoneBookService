package com.moroz.phonebookservice.dao;

import com.moroz.phonebookservice.domain.PhoneNumber;
import com.moroz.phonebookservice.domain.User;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface UserDAO {

    List<User> getAllUsers();

    User getUserById(Long id);

    List<User> getUserByName(String name);

    Map<PhoneNumber, String> getUserPhoneBook(Long id);

    List<PhoneNumber> getPhoneNumbersForContact(Long id, String contactName);

    Pair<PhoneNumber, String> getContactByPhoneNumber(Long id, PhoneNumber phoneNumber);

    boolean addContactToUserPhoneBook(Long id, PhoneNumber phoneNumber, String contactName);

    boolean createUser(String name);

    void renameUser(Long id, String newName);

    Pair<PhoneNumber, String> updatePhoneNumber(Long id, PhoneNumber currentPhoneNumber, PhoneNumber newPhoneNumber,
                                                String newContactName);

    void renameContact(Long id, String contactName, String newContactName);

    void deleteUser(Long id);

    void deletePhoneNumberFromUserPhoneBook(Long id, PhoneNumber phoneNumber);

    void deleteContactFromUserPhoneBook(Long id, String contactName);
}
