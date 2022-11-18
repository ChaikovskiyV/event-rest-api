package com.example.eventservice.model.service;

import com.example.eventservice.model.dto.EventDto;
import com.example.eventservice.model.entity.Event;

public interface EventService extends BaseService<Event> {

    Event updateEvent(EventDto eventDto, long id);

    Event createEvent(EventDto eventDto);
}