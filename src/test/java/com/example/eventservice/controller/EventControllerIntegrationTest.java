package com.example.eventservice.controller;

import com.example.eventservice.EventServiceApplication;
import com.example.eventservice.model.entity.Event;
import com.example.eventservice.testconfig.TestConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = EventServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfiguration.class)
class EventControllerIntegrationTest {
    public static final String  LOCALHOST = "http://localhost:";
    public static final String URI = "/api/v1/events";
    public static final String EVENT_TOPIC_PARAM = "eventTopic=";
    public static final String EVENT_DATE_PARAM = "eventDate=";
    public static final String ORGANIZER_PARAM = "organizer";
    public static final String SORT_PARAM = "sort=";

    private String url;
    private String eventTopic;
    private String eventDate;
    private String organizer;
    private String sort;
    private Event event;
    private List<Event> eventList;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    void init() {
        url = new StringBuilder(LOCALHOST)
                .append(port)
                .append(URI)
                .toString();
        eventTopic = "Open IT conference";
        eventDate = "10-03-2022";
        organizer = "Bestsoft Ltd";
        sort = "eventTopic";
    }

    @BeforeEach
    void setUp() {
        event = null;
        eventList = List.of();
    }

    @Test
    void registerEvent() {
    }

    @Test
    void findEventById() {
    }

    @Test
    void findEvents() {
    }

    @Test
    void updateEvent() {
    }

    @Test
    void deleteEvent() {
    }
}