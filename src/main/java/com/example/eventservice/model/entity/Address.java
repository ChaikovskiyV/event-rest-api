package com.example.eventservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

    private String addressCity;
    private String addressStreet;
    private int addressHouseNumber;

    @OneToMany(mappedBy = "address", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Event> events;

    public Address() {
        events = new ArrayList<>();
    }

    public Address(String addressCity, String addressStreet, int addressHouseNumber) {
        this.addressCity = addressCity;
        this.addressStreet = addressStreet;
        this.addressHouseNumber = addressHouseNumber;
        events = new ArrayList<>();
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String city) {
        this.addressCity = city;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String street) {
        this.addressStreet = street;
    }

    public int getAddressHouseNumber() {
        return addressHouseNumber;
    }

    public void setAddressHouseNumber(int houseNumber) {
        this.addressHouseNumber = houseNumber;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return addressHouseNumber == address.addressHouseNumber && Objects.equals(addressCity, address.addressCity) &&
                Objects.equals(addressStreet, address.addressStreet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressCity, addressStreet, addressHouseNumber);
    }

    @Override
    public String toString() {
        return new StringBuffer("Address{")
                .append(super.toString())
                .append("addressCity='")
                .append(addressCity)
                .append("', addressStreet='")
                .append(addressStreet)
                .append("', addressHouseNumber=")
                .append(addressHouseNumber)
                .append('}')
                .toString();
    }
}