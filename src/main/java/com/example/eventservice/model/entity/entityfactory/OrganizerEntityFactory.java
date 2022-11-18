package com.example.eventservice.model.entity.entityfactory;

import com.example.eventservice.model.dto.OrganizerDto;
import com.example.eventservice.model.entity.Organizer;
import org.springframework.stereotype.Component;

@Component
public class OrganizerEntityFactory implements EntityFactory<Organizer, OrganizerDto> {
    @Override
    public Organizer buildEntityFromDto(OrganizerDto dto) {
        return new Organizer(dto.getOrganizerName(), dto.getOrganizerEmail(), dto.getOrganizerTelephoneNumber());
    }
}
