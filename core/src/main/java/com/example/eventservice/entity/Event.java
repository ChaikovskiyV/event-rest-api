package com.example.eventservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Event extends BaseEntity {
    private String topic;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "events_organizers",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "organizer_id"))
    private Set<Organizer> organizers;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime eventDate;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Address address;

    public Event() {
        organizers = new HashSet<>();
    }

    public Event(String topic, String description, Set<Organizer> organizers, LocalDateTime eventDate, Address address) {
        this.topic = topic;
        this.description = description;
        this.organizers = organizers;
        this.eventDate = eventDate;
        this.address = address;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Organizer> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(Set<Organizer> organizers) {
        this.organizers = organizers;
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
        return topic.equals(event.topic) && description.equals(event.description) &&
                organizers.equals(event.organizers) && eventDate.equals(event.eventDate) && address.equals(event.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, description, organizers, eventDate, address);
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("Event{")
                .append(super.toString())
                .append("topic='")
                .append(topic)
                .append(", description='")
                .append(description)
                .append(", organizers=")
                .append(organizers)
                .append(", eventDate=")
                .append(eventDate)
                .append(", address=")
                .append(address)
                .append('}')
                .toString();
    }
}
