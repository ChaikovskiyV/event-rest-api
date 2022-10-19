package com.example.eventservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Organizer extends BaseEntity {
    private String name;
    private String email;
    private String telephoneNumber;

    @ManyToMany(mappedBy = "organizers")
    @JsonIgnore
    private List<Event> events;

    public Organizer() {
        events = new ArrayList<>();
    }

    public Organizer(String name, String email, String telephoneNumber) {
        this.name = name;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        events = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
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
        Organizer organizer = (Organizer) o;
        return name.equals(organizer.name) && email.equals(organizer.email) && telephoneNumber.equals(organizer.telephoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, telephoneNumber);
    }

    @Override
    public String toString() {
        return new StringBuffer("Organizer{")
                .append(super.toString())
                .append("name='")
                .append(name)
                .append(", email='")
                .append(email)
                .append(", telephoneNumber='")
                .append(telephoneNumber)
                .append('}')
                .toString();
    }
}