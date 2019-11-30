package com.moroz.phonebookservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
