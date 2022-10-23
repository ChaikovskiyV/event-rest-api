package com.example.eventservice.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class AddressDto implements BaseDto {

    @NotEmpty(message = "A city name can't be empty.")
    @Length(min = 3, max = 20, message = "A city name length has to be from 3 to 20 symbols.")
    @Pattern(regexp = "\\w*", message = "A city name has to contain only letters.")
    private String addressCity;

    @NotEmpty(message = "A street name can't be empty.")
    @Length(min = 3, max = 20, message = "A street name length has to be from 3 to 20 symbols.")
    @Pattern(regexp = "\\w*", message = "A street name has to contain only letters.")
    private String addressStreet;
    @Min(value = 1, message = "House number has to be positive.")
    private int addressHouseNumber;

    public AddressDto() {
    }

    public AddressDto(String addressCity, String addressStreet, int addressHouseNumber) {
        this.addressCity = addressCity;
        this.addressStreet = addressStreet;
        this.addressHouseNumber = addressHouseNumber;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public int getAddressHouseNumber() {
        return addressHouseNumber;
    }

    public void setAddressHouseNumber(int addressHouseNumber) {
        this.addressHouseNumber = addressHouseNumber;
    }

    @Override
    public String toString() {
        return new StringBuffer("Address {")
                .append("city='")
                .append(addressCity)
                .append("', street='")
                .append(addressStreet)
                .append("', addressHouseNumber=")
                .append(addressHouseNumber)
                .append('}')
                .toString();
    }
}