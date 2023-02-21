package com.example.eventservice.controller;

import com.example.eventservice.EventServiceApplication;
import com.example.eventservice.model.dto.AddressDto;
import com.example.eventservice.model.dto.EventDto;
import com.example.eventservice.model.dto.OrganizerDto;
import com.example.eventservice.model.entity.Address;
import com.example.eventservice.model.entity.Event;
import com.example.eventservice.model.entity.Organizer;
import com.example.eventservice.model.util.StringDateParser;
import com.example.eventservice.testconfig.TestConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = EventServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestConfiguration.class)
class EventControllerIntegrationTest {
    public static final String LOCALHOST = "http://localhost:";
    public static final String URI = "/api/v1/events";
    public static final String EVENT_TOPIC_PARAM = "eventTopic=";
    public static final String EVENT_DATE_PARAM = "eventDate=";
    public static final String ORGANIZER_PARAM = "organizer=";
    public static final String SORT_PARAM = "sort=";

    private String url;
    private String eventTopic;
    private String eventDate;
    private String organizer;
    private String sort;
    private EventDto eventDto;
    private Event event;
    private List<Event> eventList;
    private long id;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    void init() {
        eventTopic = "How to be a doctor";
        eventDate = "16-10 10-03-2023";
        organizer = "First";
        sort = "eventDate";
        eventList = List.of(
                new Event(
                        "How to be a doctor",
                        "About medicine best practice",
                        new Organizer("Second", "second@second.com", "+375446116466"),
                        StringDateParser.parseStringToDate("15-10 10-04-2023"),
                        new Address("Gomel", "Vaneeva", 8)
                ),
                new Event(
                        "How to be a farmer",
                        "About growing plant best practice",
                        new Organizer("Third", "third@third.com", "+375446116467"),
                        StringDateParser.parseStringToDate("16-10 10-03-2023"),
                        new Address("Kiev", "Mira", 14)
                ),
                new Event(
                        "How to be a programmer",
                        "About programming best practice",
                        new Organizer("First", "first@first.com", "+375446116465"),
                        StringDateParser.parseStringToDate("14-10 10-03-2023"),
                        new Address("Minsk", "Gikalo", 5)
                )
        );
        eventDto = new EventDto(
                "How to be a coach",
                "About coaching best practice",
                new OrganizerDto("Fourth", "fourth@fourth.com", "+375446116468"),
                "15-10 10-05-2023",
                new AddressDto("Minsk", "Gikalo", 5)
        );
        event = new Event(
                "How to be a coach",
                "About coaching best practice",
                new Organizer("Fourth", "fourth@fourth.com", "+375446116468"),
                StringDateParser.parseStringToDate("15-10 10-05-2023"),
                new Address("Minsk", "Gikalo", 5)
        );

    }

    @BeforeEach
    void setUp() {
        url = new StringBuilder(LOCALHOST)
                .append(port)
                .append(URI)
                .toString();
    }

    @Test
    void testRegisterEvent() {
        Event created = restTemplate.postForObject(url, eventDto, Event.class);

        assertEquals(event, created);
    }

    @Test
    void testRegisterEventWhenEventDataNotCorrect() {
        EventDto notCorrectEventDto = new EventDto(
                "",
                "About coaching best practice",
                new OrganizerDto("Fourth", "fourth@fourth.com", "+375446116468"),
                "15-10 10-05-2023",
                new AddressDto("Minsk", "Gikalo", 5)
        );

        assertDoesNotThrow(() -> restTemplate.postForObject(url, notCorrectEventDto, Event.class));
    }

    @Test
    void testFindEventById() {
        long id = 7;
        String parameterizedUrl = new StringBuilder(url).append('/')
                .append(id)
                .toString();
        Event foundEvent = restTemplate.getForObject(parameterizedUrl, Event.class);

        assertEquals(eventList.get(2), foundEvent);
    }

    @Test
    void testFindEventByIdWhenIdNotExists() {
        id = 1000;
        String parameterizedUrl = new StringBuilder(url).append('/')
                .append(id)
                .toString();

        assertDoesNotThrow(() -> restTemplate.getForObject(parameterizedUrl, Event.class));
    }

    @Test
    void testFindEventByIdWhenIdNotCorrect() {
        id = 0;
        String parameterizedUrl = new StringBuilder(url).append('/')
                .append(id)
                .toString();

        assertDoesNotThrow(() -> restTemplate.getForObject(parameterizedUrl, Event.class));
    }

    @Test
    void testFindEventsWhenNoParametersProvided() {
        Event[] events = restTemplate.getForObject(url, Event[].class);

        assertArrayEquals(eventList.toArray(), events);
    }

    @Test
    void testFindEventsWhenEventTopicParamProvided() {
        String parameterizedUrl = new StringBuilder(url).append('?')
                .append(EVENT_TOPIC_PARAM)
                .append(eventTopic)
                .toString();

        Event[] events = restTemplate.getForObject(parameterizedUrl, Event[].class);

        assertArrayEquals(new Event[]{eventList.get(0)}, events);
    }

    @Test
    void testFindEventsWhenWrongEventTopicParamProvided() {
        String parameterizedUrl = new StringBuilder(url).append('?')
                .append(EVENT_TOPIC_PARAM)
                .append(" ")
                .toString();

        Event[] events = restTemplate.getForObject(parameterizedUrl, Event[].class);

        assertEquals(0, events.length);
    }

    @Test
    void testFindEventsWhenEventDateParamProvided() {
        String parameterizedUrl = new StringBuilder(url).append('?')
                .append(EVENT_DATE_PARAM)
                .append(eventDate)
                .toString();

        Event[] events = restTemplate.getForObject(parameterizedUrl, Event[].class);

        assertArrayEquals(new Event[]{eventList.get(1)}, events);
    }

    @Test
    void testFindEventsWhenWrongEventDateParamProvided() {
        String parameterizedUrl = new StringBuilder(url).append('?')
                .append(EVENT_DATE_PARAM)
                .append(" ")
                .toString();

        Event[] events = restTemplate.getForObject(parameterizedUrl, Event[].class);

        assertEquals(0, events.length);
    }

    @Test
    void testFindEventsWhenEventOrganizerProvided() {
        String parameterizedUrl = new StringBuilder(url).append('?')
                .append(ORGANIZER_PARAM)
                .append(organizer)
                .toString();

        Event[] events = restTemplate.getForObject(parameterizedUrl, Event[].class);

        assertArrayEquals(new Event[]{eventList.get(2)}, events);
    }

    @Test
    void testFindEventsWhenWrongEventOrganizerProvided() {
        String parameterizedUrl = new StringBuilder(url).append('?')
                .append(ORGANIZER_PARAM)
                .append(" ")
                .toString();

        Event[] events = restTemplate.getForObject(parameterizedUrl, Event[].class);

        assertEquals(0, events.length);
    }

    @Test
    void testFindEventsWhenSortParamProvided() {
        String parameterizedUrl = new StringBuilder(url).append('?')
                .append(SORT_PARAM)
                .append(sort)
                .toString();

        Event[] events = restTemplate.getForObject(parameterizedUrl, Event[].class);

        assertArrayEquals(new Event[] {eventList.get(2), eventList.get(1), eventList.get(0)}, events);
    }

    @Test
    void testFindEventsWhenWrongSortParamProvided() {
        String parameterizedUrl = new StringBuilder(url).append('?')
                .append(SORT_PARAM)
                .append("sort")
                .toString();

        Event[] events = restTemplate.getForObject(parameterizedUrl, Event[].class);

        assertArrayEquals(eventList.toArray(), events);
    }

    @Test
    void testUpdateEvent() {
        int id = 8;
        String parameterizedUrl = new StringBuilder(url).append("/{id}").toString();
        EventDto eventDto = new EventDto(
                "How to be a doctor",
                "About medicine best practice",
                new OrganizerDto("Second", "second@second.com", "+375446116466"),
                "17-10 10-04-2023",
                new AddressDto("Gomel", "Vaneeva", 8)
        );
        HttpEntity<EventDto> httpEntity = new HttpEntity<>(eventDto);

        ResponseEntity<Event> updated = restTemplate.exchange(parameterizedUrl, HttpMethod.PUT, httpEntity, Event.class, id);

        assertTrue(updated.getBody() != null && updated.getBody().getId() != 0 &&
                updated.getBody().getEventTopic().equals("How to be a doctor") &&
                updated.getBody().getEventDate().equals(StringDateParser.parseStringToDate("17-10 10-04-2023")));

    }

    @Test
    void testUpdateEventWhenEventIdNotExists() {
        int id = 1000;
        String parameterizedUrl = new StringBuilder(url).append("/{id}").toString();
        EventDto eventDto = new EventDto(
                "How to be a doctor",
                "About medicine best practice",
                new OrganizerDto("Second", "second@second.com", "+375446116466"),
                "17-10 10-04-2023",
                new AddressDto("Gomel", "Vaneeva", 8)
        );
        HttpEntity<EventDto> httpEntity = new HttpEntity<>(eventDto);

       ResponseEntity <Event> updated = restTemplate.exchange(parameterizedUrl, HttpMethod.PUT, httpEntity, Event.class, id);

       assertTrue(updated.getBody() != null && updated.getBody().getId() == 0 &&
               updated.getBody().getEventTopic() == null);
    }

    @Test
    void testDeleteEvent() {
        int id = 7;
        String parameterizedUrl = new StringBuilder(url).append('/')
                .append(id)
                .toString();

        restTemplate.delete(parameterizedUrl);

        Event[] events = restTemplate.getForObject(url, Event[].class);

        assertTrue(Arrays.stream(events).noneMatch(e -> e.getId() == id));
    }

    @Test
    void testDeleteEventWhenEventIdNotExists() {
        int id = 1000;
        String parameterizedUrl = new StringBuilder(url).append('/')
                .append(id)
                .toString();

        assertDoesNotThrow(() -> restTemplate.delete(parameterizedUrl));
    }
}