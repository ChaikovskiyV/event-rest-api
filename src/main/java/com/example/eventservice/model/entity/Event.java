package com.example.eventservice.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "events")
public class Event extends BaseEntity {
    private String eventTopic;
    private String eventDescription;
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "organizer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Organizer organizer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime eventDate;
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Address address;

    public Event() {
    }

    public Event(String eventTopic, String eventDescription, Organizer organizer, LocalDateTime eventDate, Address address) {
        this.eventTopic = eventTopic;
        this.eventDescription = eventDescription;
        this.organizer = organizer;
        this.eventDate = eventDate;
        this.address = address;
    }

    public String getEventTopic() {
        return eventTopic;
    }

    public void setEventTopic(String topic) {
        this.eventTopic = topic;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String description) {
        this.eventDescription = description;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventTopic, event.eventTopic) && Objects.equals(eventDescription, event.eventDescription) &&
                Objects.equals(organizer, event.organizer) && Objects.equals(eventDate, event.eventDate) &&
                Objects.equals(address, event.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventTopic, eventDescription, organizer, eventDate, address);
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("Event{")
                .append(super.toString())
                .append("eventTopic='")
                .append(eventTopic)
                .append("', eventDescription='")
                .append(eventDescription)
                .append("', organizer=")
                .append(organizer)
                .append(", eventDate=")
                .append(eventDate)
                .append(", address=")
                .append(address)
                .append('}')
                .toString();
    }
}