package com.example.eventservice.model.dao.impl;

import com.example.eventservice.model.dao.EventDao;
import com.example.eventservice.model.entity.Event;
import com.example.eventservice.model.util.querybuilder.EventSortQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.eventservice.model.dao.DatabaseQueries.*;

@Repository
public class EventDaoImpl implements EventDao {
    private final EventSortQueryBuilder queryBuilder;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public EventDaoImpl(EventSortQueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    @Override
    public Event findById(long id) {
        return entityManager.find(Event.class, id);
    }

    @Override
    public List<Event> findAll(String sortParams) {
        String sortQuery = queryBuilder.buildSortQuery(FIND_ALL_EVENTS, sortParams);

        return entityManager.createQuery(sortQuery, Event.class).getResultList();
    }

    @Override
    public Event update(Event event) {
        return entityManager.merge(event);
    }

    @Override
    public void delete(Event event) {
        entityManager.remove(event);
    }

    @Override
    public Event insertEvent(Event event) {
        return entityManager.merge(event);
    }

    @Override
    public List<Event> findEventByTopic(String topic, String sortParams) {
        String sortQuery = queryBuilder.buildSortQuery(FIND_EVENT_BY_TOPIC, sortParams);

        return entityManager.createQuery(sortQuery, Event.class)
                .setParameter(1, topic)
                .getResultList();
    }

    @Override
    public List<Event> findEventByOrganizerName(String organizerName, String sortParams) {
        String sortQuery = queryBuilder.buildSortQuery(FIND_EVENT_BY_ORGANIZER, sortParams);

        return entityManager.createQuery(sortQuery, Event.class)
                .setParameter(1, organizerName)
                .getResultList();
    }

    @Override
    public List<Event> findEventByDate(LocalDateTime eventDate, String sortParams) {
        String sortQuery = queryBuilder.buildSortQuery(FIND_EVENT_BY_DATE, sortParams);

        return entityManager.createQuery(sortQuery, Event.class)
                .setParameter(1, eventDate)
                .getResultList();
    }
}