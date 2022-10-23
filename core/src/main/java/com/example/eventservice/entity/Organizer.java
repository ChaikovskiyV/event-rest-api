package com.example.eventservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "organizers")
public class Organizer extends BaseEntity {
    private String organizerName;
    private String organizerEmail;
    private String organizerTelephoneNumber;

    @OneToMany(mappedBy = "organizer")
    @JsonIgnore
    private List<Event> events;

    public Organizer() {
        events = new ArrayList<>();
    }

    public Organizer(String organizerName, String organizerEmail, String organizerTelephoneNumber) {
        this.organizerName = organizerName;
        this.organizerEmail = organizerEmail;
        this.organizerTelephoneNumber = organizerTelephoneNumber;
        events = new ArrayList<>();
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String name) {
        this.organizerName = name;
    }

    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public void setOrganizerEmail(String email) {
        this.organizerEmail = email;
    }

    public String getOrganizerTelephoneNumber() {
        return organizerTelephoneNumber;
    }

    public void setOrganizerTelephoneNumber(String telephoneNumber) {
        this.organizerTelephoneNumber = telephoneNumber;
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
        return Objects.equals(organizerName, organizer.organizerName) && Objects.equals(organizerEmail, organizer.organizerEmail) &&
                Objects.equals(organizerTelephoneNumber, organizer.organizerTelephoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizerName, organizerEmail, organizerTelephoneNumber);
    }

    @Override
    public String toString() {
        return new StringBuffer("Organizer{")
                .append(super.toString())
                .append("organizerName='")
                .append(organizerName)
                .append("', organizerEmail='")
                .append(organizerEmail)
                .append("', organizerTelephoneNumber='")
                .append(organizerTelephoneNumber)
                .append("'}")
                .toString();
    }
}