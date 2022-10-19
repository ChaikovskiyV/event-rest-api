package com.example.eventservice.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class AddressDto {

    @NotEmpty(message = "A city name can't be empty.")
    @Length(min = 3, max = 20, message = "A city name length has to be from 3 to 20 symbols.")
    @Pattern(regexp = "\\w*", message = "A city name has to contain only letters.")
    private String city;

    @NotEmpty(message = "A street name can't be empty.")
    @Length(min = 3, max = 20, message = "A street name length has to be from 3 to 20 symbols.")
    @Pattern(regexp = "\\w*", message = "A street name has to contain only letters.")
    private String street;
    @Min(value = 1, message = "House number has to be positive.")
    private int houseNumber;

    public AddressDto() {
    }

    public AddressDto(String city, String street, int houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }
}