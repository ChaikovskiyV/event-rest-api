package com.example.eventservice.model.entity.entityfactory;

import com.example.eventservice.model.dto.AddressDto;
import com.example.eventservice.model.dto.EventDto;
import com.example.eventservice.model.dto.OrganizerDto;
import com.example.eventservice.model.entity.Address;
import com.example.eventservice.model.entity.Event;
import com.example.eventservice.model.entity.Organizer;
import com.example.eventservice.model.util.StringDateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventEntityFactory implements EntityFactory<Event, EventDto> {
    private final EntityFactory<Organizer, OrganizerDto> organizerFactory;
    private final EntityFactory<Address, AddressDto> addressFactory;

    @Autowired
    public EventEntityFactory(OrganizerEntityFactory organizerFactory,
                              AddressEntityFactory addressFactory) {
        this.organizerFactory = organizerFactory;
        this.addressFactory = addressFactory;
    }

    @Override
    public Event buildEntityFromDto(EventDto dto) {
        Organizer organizer = organizerFactory.buildEntityFromDto(dto.getOrganizer());
        Address eventAddress = addressFactory.buildEntityFromDto(dto.getAddress());
        LocalDateTime eventDate = StringDateParser.parseStringToDate(dto.getEventDate());

        return new Event(dto.getEventTopic(), dto.getEventDescription(), organizer, eventDate, eventAddress);
    }
}