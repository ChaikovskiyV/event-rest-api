package com.example.eventservice.service;

import com.example.eventservice.dto.EventDto;
import com.example.eventservice.entity.Event;

public interface EventService extends BaseService<Event> {

    Event updateEvent(EventDto eventDto, long id);

    Event createEvent(EventDto eventDto);
}