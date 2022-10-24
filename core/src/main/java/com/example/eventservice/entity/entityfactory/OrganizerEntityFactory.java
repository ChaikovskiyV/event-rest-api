package com.example.eventservice.entity.entityfactory;

import com.example.eventservice.dto.OrganizerDto;
import com.example.eventservice.entity.Organizer;
import org.springframework.stereotype.Component;

@Component
public class OrganizerEntityFactory implements EntityFactory<Organizer, OrganizerDto> {
    @Override
    public Organizer buildEntityFromDto(OrganizerDto dto) {
        return new Organizer(dto.getOrganizerName(), dto.getOrganizerEmail(), dto.getOrganizerTelephoneNumber());
    }
}
