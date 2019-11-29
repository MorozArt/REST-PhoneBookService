package com.moroz.phonebookservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.HashMap;
import java.util.Map;

public class User {

    @JsonView(Views.IdName.class)
    private Long id;
    @JsonView(Views.IdName.class)
    private String name;
    @JsonView(Views.Full.class)
    private Map<PhoneNumber, String> phoneBook;

    public User(@JsonProperty(value = "name") String name) {
        this.name = name;
        phoneBook = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<PhoneNumber, String> getPhoneBook() {
        return phoneBook;
    }

    public void addContactToPhoneBook(PhoneNumber phoneNumber, String contactName) {
        phoneBook.put(phoneNumber, contactName);
    }

    public void deleteContactFromPhoneBook(PhoneNumber phoneNumber) {
        phoneBook.remove(phoneNumber);

    }
}
