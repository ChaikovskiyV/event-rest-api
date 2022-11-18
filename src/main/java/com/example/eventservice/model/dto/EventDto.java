package com.example.eventservice.model.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class EventDto implements BaseDto {
    @NotEmpty(message = "A topic can't be empty.")
    @Length(min = 5, max = 50, message = "A topic length has to be from 5 to 50 symbols.")
    @Pattern(regexp = "[^<>].*", message = "A topic can't include symbols '<' and '>'.")
    private String eventTopic;
    @NotEmpty(message = "Description can't be empty.")
    @Length(min = 20, max = 200, message = "Description length has to be from 20 to 200 symbols.")
    @Pattern(regexp = "[^<>].*", message = "Description can't include symbols '<' and '>'.")
    private String eventDescription;
    @NotNull(message = "An event has to have an organizer.")
    private OrganizerDto organizer;
    @NotEmpty(message = "An event has to have a date.")
    @Pattern(regexp = "\\d{2}-\\d{2}\\s\\d{2}-\\d{2}-\\d{4}", message = "Date has to be in follow format: HH-mm dd-MM-yyyy.")
    private String eventDate;
    @NotNull(message = "En event has to have an address.")
    private AddressDto address;

    public EventDto() {
    }

    public EventDto(String eventTopic, String eventDescription, OrganizerDto organizer, String eventDate, AddressDto address) {
        this.eventTopic = eventTopic;
        this.eventDescription = eventDescription;
        this.organizer = organizer;
        this.eventDate = eventDate;
        this.address = address;
    }

    public String getEventTopic() {
        return eventTopic;
    }

    public void setEventTopic(String eventTopic) {
        this.eventTopic = eventTopic;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public OrganizerDto getOrganizer() {
        return organizer;
    }

    public void setOrganizer(OrganizerDto organizer) {
        this.organizer = organizer;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return new StringBuffer("Event{")
                .append("topic='")
                .append(eventTopic)
                .append("', description='")
                .append(eventDescription)
                .append("', organizer=")
                .append(organizer)
                .append("', date='")
                .append(eventDate)
                .append("', address=")
                .append(address)
                .append('}')
                .toString();
    }
}