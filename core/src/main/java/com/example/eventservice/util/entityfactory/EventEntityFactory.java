package com.example.eventservice.util.entityfactory;

import com.example.eventservice.dto.AddressDto;
import com.example.eventservice.dto.EventDto;
import com.example.eventservice.dto.OrganizerDto;
import com.example.eventservice.entity.Address;
import com.example.eventservice.entity.Event;
import com.example.eventservice.entity.Organizer;
import com.example.eventservice.util.StringDateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventEntityFactory implements EntityFactory<Event, EventDto> {
    private final EntityFactory<Organizer, OrganizerDto> organizerBuilder;
    private final EntityFactory<Address, AddressDto> addressBuilder;

    @Autowired
    public EventEntityFactory(EntityFactory<Organizer, OrganizerDto> organizerBuilder,
                              EntityFactory<Address, AddressDto> addressBuilder) {
        this.organizerBuilder = organizerBuilder;
        this.addressBuilder = addressBuilder;
    }

    @Override
    public Event buildEntityFromDto(EventDto dto) {
        Organizer organizer = organizerBuilder.buildEntityFromDto(dto.getOrganizer());
        Address eventAddress = addressBuilder.buildEntityFromDto(dto.getAddress());
        LocalDateTime eventDate = StringDateParser.parseStringToDate(dto.getEventDate());

        return new Event(dto.getEventTopic(), dto.getEventDescription(), organizer, eventDate, eventAddress);
    }
}