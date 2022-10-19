package com.example.eventservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.*;

@Entity
public class Address extends BaseEntity {

    private String city;
    private String street;
    private int houseNumber;

    @OneToMany(mappedBy = "address", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Event> events;

    public Address() {
        events = new ArrayList<>();
    }

    public Address(String city, String street, int houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        events = new ArrayList<>();
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
        return houseNumber == address.houseNumber && city.equals(address.city) && street.equals(address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, houseNumber);
    }

    @Override
    public String toString() {
        return new StringBuffer("Address{")
                .append(super.toString())
                .append("city='")
                .append(city)
                .append(", street='")
                .append(street)
                .append(", houseNumber=")
                .append(houseNumber)
                .append('}')
                .toString();
    }
}