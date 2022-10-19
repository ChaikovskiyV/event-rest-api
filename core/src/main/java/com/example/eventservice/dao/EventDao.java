package com.example.eventservice.dao;

import com.example.eventservice.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventDao extends BaseDao<Event> {

    Event insertEvent(Event event);

    List<Event> findEventByTopic(String topic);

    List<Event> findEventByOrganizerName(String organizerName);

    List<Event> findEventByDescription(LocalDateTime eventDate);
}