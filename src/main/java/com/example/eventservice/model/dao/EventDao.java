package com.example.eventservice.model.dao;

import com.example.eventservice.model.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventDao extends BaseDao<Event> {

    Event insertEvent(Event event);

    List<Event> findEventByTopic(String topic, String sortParams);

    List<Event> findEventByOrganizerName(String organizerName, String sortParams);

    List<Event> findEventByDate(LocalDateTime eventDate, String sortParams);
}