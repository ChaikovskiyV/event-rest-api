package com.example.eventservice.dao;

public final class DatabaseQueries {
    public static final String FIND_ALL_EVENTS = "FROM Event e";
    public static final String FIND_EVENT_BY_TOPIC = "FROM Event e WHERE e.eventTopic LIKE CONCAT('%', ?1, '%')";
    public static final String FIND_EVENT_BY_DATE = "FROM Event e WHERE e.eventDate=?1";
    public static final String FIND_EVENT_BY_ORGANIZER = "FROM Event e JOIN FETCH e.organizer o WHERE o.organizerName LIKE CONCAT('%', ?1, '%')";
    public static final String FIND_ORGANIZER_BY_NAME = "FROM Organizer o WHERE o.organizerName=?1";
    public static final String FIND_ADDRESS_BY_CITY = "FROM Address a WHERE a.addressCity=?1";

    private DatabaseQueries() {
    }
}