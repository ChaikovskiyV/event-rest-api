package com.example.eventservice.dto;

import com.example.eventservice.entity.Address;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

public class EventDto {
    @NotEmpty(message = "A topic can't be empty.")
    @Length(min = 5, max = 50, message = "A topic length has to be from 5 to 50 symbols.")
    @Pattern(regexp = "[^<>]", message = "A topic can't include symbols '<' and '>'.")
    private String topic;
    @NotEmpty(message = "Description can't be empty.")
    @Length(min = 20, max = 200, message = "Description length has to be from 20 to 200 symbols.")
    @Pattern(regexp = "[^<>]", message = "Description can't include symbols '<' and '>'.")
    private String description;
    @NotEmpty(message = "An event has to have at least one organizer.")
    private List<OrganizerDto> organizers;
    @NotEmpty(message = "An event has to have a date.")
    @Pattern(regexp = "\\d{2}-\\d{2}\\s\\d{2}\\.\\d{2}\\.\\d{4}", message = "Date has to be in follow format: HH-mm dd.MM.yyyy.")
    private String eventDate;
    @NotNull(message = "En event has to have an address.")
    private Address address;

    public EventDto() {
        organizers = new ArrayList<>();
    }

    public EventDto(String topic, String description, List<OrganizerDto> organizers, String eventDate, Address address) {
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

    public List<OrganizerDto> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(List<OrganizerDto> organizers) {
        this.organizers = organizers;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}