package com.moroz.phonebookservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class PhoneNumber {

    private Integer countryCode;
    private Integer operatorCode;
    private Integer number;

    public PhoneNumber(@JsonProperty(value = "countryCode") Integer countryCode,
                       @JsonProperty(value = "operatorCode") Integer operatorCode,
                       @JsonProperty(value = "number") Integer number) {

        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.number = number;
    }

    @Override
    public String toString() {
        return "+"+countryCode+" ("+operatorCode+") "+number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(countryCode, that.countryCode) &&
                Objects.equals(operatorCode, that.operatorCode) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, operatorCode, number);
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(Integer operatorCode) {
        this.operatorCode = operatorCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
