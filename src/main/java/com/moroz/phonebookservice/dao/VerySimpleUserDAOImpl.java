package com.moroz.phonebookservice.dao;

import com.moroz.phonebookservice.domain.PhoneNumber;
import com.moroz.phonebookservice.domain.User;
import com.moroz.phonebookservice.exceptions.PhoneNumberNotFoundException;
import com.moroz.phonebookservice.exceptions.UserNotFoundException;
import javafx.util.Pair;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component("userDAO")
@Scope("singleton")
public class VerySimpleUserDAOImpl implements UserDAO {

    private static Long ID_COUNT = 0L;
    private List<User> users = new ArrayList<>();
    {
        User user = new User("John Pierce");
        user.setId(ID_COUNT++);
        user.addContactToPhoneBook(new PhoneNumber(7, 911, 8302832), "Walter Rodgers");
        user.addContactToPhoneBook(new PhoneNumber(7, 961, 2649160), "Karen Reynolds");
        users.add(user);

        user = new User("Walter Rodgers");
        user.setId(ID_COUNT++);
        user.addContactToPhoneBook(new PhoneNumber(7, 921, 2384529), "John Pierce");
        user.addContactToPhoneBook(new PhoneNumber(7, 961, 2649160), "Karen Reynolds");
        users.add(user);

        user = new User("Karen Reynolds");
        user.setId(ID_COUNT++);
        user.addContactToPhoneBook(new PhoneNumber(7, 921, 2384529), "John Pierce");
        user.addContactToPhoneBook(new PhoneNumber(7, 911, 8302832), "Walter Rodgers");
        users.add(user);
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User getUserById(Long id) {
        return getUser(id);
    }

    @Override
    public List<User> getUserByName(String name) {
        return users.stream().filter(user -> user.getName().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public Map<PhoneNumber, String> getUserPhoneBook(Long id) {
        return getUser(id).getPhoneBook();
    }

    @Override
    public List<PhoneNumber> getPhoneNumbersForContact(Long id, String contactName) {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        Map<PhoneNumber, String> userPhoneBook = getUser(id).getPhoneBook();

        for(Map.Entry<PhoneNumber, String> contact : userPhoneBook.entrySet()) {
            if(contact.getValue().equals(contactName)) {
                phoneNumbers.add(contact.getKey());
            }
        }

        if(phoneNumbers.isEmpty()) {
            throw new PhoneNumberNotFoundException();
        }

        return phoneNumbers;
    }

    @Override
    public Pair<PhoneNumber, String> getContactByPhoneNumber(Long id, PhoneNumber phoneNumber) {
        return getContact(id, phoneNumber);
    }

    @Override
    public User createUser(String name) {
        User newUser = new User(name);
        newUser.setId(ID_COUNT++);
        users.add(newUser);
        return newUser;
    }

    @Override
    public Pair<PhoneNumber, String> addContactToUserPhoneBook(Long id, PhoneNumber phoneNumber, String contactName) {
        User user = getUser(id);
        user.addContactToPhoneBook(phoneNumber, contactName);
        return new Pair<>(phoneNumber, contactName);
    }

    @Override
    public User renameUser(Long id, String newName) {
        User renamedUser = getUser(id);
        renamedUser.setName(newName);
        return renamedUser;
    }

    @Override
    public Pair<PhoneNumber, String> updatePhoneNumber(Long id, PhoneNumber currentPhoneNumber,
                                                       PhoneNumber newPhoneNumber, String newContactName) {
        Pair<PhoneNumber, String> contact = getContact(id, currentPhoneNumber);
        Map<PhoneNumber, String> userPhoneBook = getUser(id).getPhoneBook();

        userPhoneBook.remove(contact.getKey());
        if(!newContactName.isEmpty()) {
            contact = new Pair<>(newPhoneNumber, newContactName);
        } else {
            contact = new Pair<>(newPhoneNumber, contact.getValue());
        }

        userPhoneBook.put(contact.getKey(), contact.getValue());

        return contact;
    }

    @Override
    public void renameContact(Long id, String contactName, String newContactName) {
        Map<PhoneNumber, String> userPhoneBook = getUser(id).getPhoneBook();

        for(Map.Entry<PhoneNumber, String> contact : userPhoneBook.entrySet()) {
            if(contact.getValue().equals(contactName)) {
                contact.setValue(newContactName);
            }
        }
    }

    @Override
    public void deleteUser(Long id) {
        users.remove(getUser(id));
    }

    @Override
    public void deletePhoneNumberFromUserPhoneBook(Long id, PhoneNumber phoneNumber) {
        getUser(id).getPhoneBook().remove(phoneNumber);
    }

    @Override
    public void deleteContactFromUserPhoneBook(Long id, String contactName) {
        Map<PhoneNumber, String> userPhoneBook = getUser(id).getPhoneBook();
        userPhoneBook.entrySet().removeIf(entry -> entry.getValue().equals(contactName));
    }

    private User getUser(Long id) {
        return users.stream().filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

    private Pair<PhoneNumber, String> getContact(Long id, PhoneNumber phoneNumber) {
        Map<PhoneNumber, String> userPhoneBook = getUser(id).getPhoneBook();
        String contactName = userPhoneBook.get(phoneNumber);
        if(contactName != null) {
            return new Pair<>(phoneNumber, contactName);
        } else {
            throw new PhoneNumberNotFoundException();
        }
    }
}
