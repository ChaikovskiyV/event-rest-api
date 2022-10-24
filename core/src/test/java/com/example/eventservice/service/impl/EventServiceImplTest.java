package com.example.eventservice.service.impl;

import com.example.eventservice.dao.AddressDao;
import com.example.eventservice.dao.EventDao;
import com.example.eventservice.dao.OrganizerDao;
import com.example.eventservice.dto.AddressDto;
import com.example.eventservice.dto.EventDto;
import com.example.eventservice.dto.OrganizerDto;
import com.example.eventservice.entity.Address;
import com.example.eventservice.entity.Event;
import com.example.eventservice.entity.Organizer;
import com.example.eventservice.entity.entityfactory.AddressEntityFactory;
import com.example.eventservice.entity.entityfactory.EventEntityFactory;
import com.example.eventservice.entity.entityfactory.OrganizerEntityFactory;
import com.example.eventservice.exception.ApplicationDuplicateException;
import com.example.eventservice.exception.ApplicationNotFoundException;
import com.example.eventservice.exception.ApplicationNotValidDataException;
import com.example.eventservice.util.querybuilder.EventSortQueryBuilder;
import com.example.eventservice.util.validator.DataValidator;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventServiceImplTest {
    private static final String TOPIC_PARAM = "eventTopic";
    private static final String DATE_PARAM = "eventDate";
    private static final String ORGANIZER_PARAM = "organizer";
    private static final String SORT_PARAM = "sort";
    private static final String NAME_REGEX = "[^><]{3,20}";
    private static final String TOPIC_REGEX = "[^><]{5,50}";
    private static final String DESCRIPTION_REGEX = "[^><]{20,200}";
    private static final String DATE_REGEX = "(([01]\\d)|(2[0-3]))-[0-5]\\d ((0[1-9])|([1-2]\\d)|(3[01]))-((0[1-9])|(1[0-2]))-\\d{4}";
    private static final String REQUEST_PARAM_NAME_REGEX = "[\\w\\s]{3,}";

    private AutoCloseable closeable;

    @InjectMocks
    private EventServiceImpl eventService;
    @Spy
    private EventDao eventDaoMock;
    @Spy
    private AddressDao addressDaoMock;
    @Spy
    private OrganizerDao organizerDaoMock;
    @Spy
    private DataValidator dataValidatorMock;
    @Spy
    private OrganizerEntityFactory organizerFactoryMock;
    @Spy
    private AddressEntityFactory addressFactoryMock;
    @Spy
    private EventEntityFactory eventFactoryMock = new EventEntityFactory(organizerFactoryMock, addressFactoryMock);
    @Spy
    private EventSortQueryBuilder eventSortQueryBuilderMock;

    private Map<String, String> requestParamMap;
    private String eventTopic;
    private String eventDate;
    private String organizerName;
    private String sort;
    private long id;
    private long notCorrectId;
    private EventDto eventDto;
    private Event eventOne;
    private Event eventTwo;
    private List<Event> events;
    private Address address;
    private Organizer organizer;

    @BeforeAll
    void init() {

        eventTopic = "Event topic";
        eventDate = "12-10 25-10-2030";
        organizerName = "The best organizer";
        sort = "eventTopic asc, eventDate desc, organizerName asc";
        id = 1;
        notCorrectId = 0;

        String eventDescription = "There is some description";
        String secondOrganizerName = "Second organizer";
        String organizerEmail = "tbo@gmail.com";
        String telephoneNumber = "+375444444444";
        LocalDateTime eventOneDate = LocalDateTime.of(2030, 10, 25, 12, 10);
        LocalDateTime eventTwoDate = LocalDateTime.of(2031, 12, 27, 12, 10);
        String city = "Minsk";
        String street = "Mira";
        int houseNumber = 55;

        eventDto = new EventDto(eventTopic, eventDescription,
                new OrganizerDto(organizerName, organizerEmail, telephoneNumber),
                eventDate, new AddressDto(city, street, houseNumber));

        organizer = new Organizer(organizerName, organizerEmail, telephoneNumber);

        address = new Address(city, street, houseNumber);

        eventOne = new Event(eventTopic, eventDescription, organizer, eventOneDate, address);

        eventTwo = new Event(eventTopic, eventDescription,
                new Organizer(secondOrganizerName, organizerEmail, telephoneNumber),
                eventTwoDate, address);

        events = List.of(eventOne, eventTwo);
    }

    @BeforeEach
    void setUp() {
        requestParamMap = new HashMap<>();

        closeable = MockitoAnnotations.openMocks(this);

        //Configure dataValidatorMock
        Mockito.doReturn(true).when(dataValidatorMock)
                .isNumberValid(Mockito.longThat(l -> l > 0));
        Mockito.doReturn(true).when(dataValidatorMock).isEventTopicValid(Mockito.matches(TOPIC_REGEX));
        Mockito.doReturn(true).when(dataValidatorMock).isNameValid(Mockito.matches(NAME_REGEX));
        Mockito.doReturn(true).when(dataValidatorMock).isEventDateValid(Mockito.matches(DATE_REGEX));
        Mockito.doReturn(true).when(dataValidatorMock)
                .isRequestParamNameValid(Mockito.matches(REQUEST_PARAM_NAME_REGEX));
        Mockito.doReturn(true).when(dataValidatorMock)
                .isEventDescriptionValid(Mockito.matches(DESCRIPTION_REGEX));
        Mockito.doReturn(true).when(dataValidatorMock).isAddressValid(Mockito.any(AddressDto.class));
        Mockito.doReturn(true).when(dataValidatorMock).isOrganizerValid(Mockito.any(OrganizerDto.class));

        //Configure eventDaoMock
        Mockito.doReturn(eventOne).when(eventDaoMock).findById(Mockito.anyLong());
        Mockito.doReturn(events).when(eventDaoMock).findAll(Mockito.anyString());
        Mockito.doReturn(events).when(eventDaoMock).findEventByTopic(Mockito.anyString(), Mockito.anyString());
        Mockito.doReturn(List.of(eventOne)).when(eventDaoMock)
                .findEventByOrganizerName(Mockito.anyString(), Mockito.anyString());
        Mockito.doReturn(List.of(eventOne)).when(eventDaoMock)
                .findEventByDate(Mockito.any(LocalDateTime.class), Mockito.anyString());
        Mockito.doNothing().when(eventDaoMock).delete(Mockito.any(Event.class));
        Mockito.doReturn(eventOne).when(eventDaoMock).update(Mockito.any(Event.class));
        Mockito.doReturn(eventOne).when(eventDaoMock).insertEvent(Mockito.any(Event.class));

        //Configure organizerDaoMock and addressDaoMock
        Mockito.doReturn(List.of(organizer)).when(organizerDaoMock).findOrganizerByName(Mockito.anyString());
        Mockito.doReturn(List.of(address)).when(addressDaoMock).findAddressByCity(Mockito.anyString());

        //Configure organizerFactoryMock, addressFactoryMock and eventFactoryMock
        Mockito.doReturn(organizer).when(organizerFactoryMock).buildEntityFromDto(Mockito.any(OrganizerDto.class));
        Mockito.doReturn(address).when(addressFactoryMock).buildEntityFromDto(Mockito.any(AddressDto.class));
        Mockito.doReturn(eventOne).when(eventFactoryMock).buildEntityFromDto(Mockito.any(EventDto.class));

        //Configure eventSortQueryBuilderMock
        Mockito.doReturn("").when(eventSortQueryBuilderMock)
                .buildSortQuery(Mockito.anyString(), Mockito.anyString());
        Mockito.doReturn("").when(eventSortQueryBuilderMock).buildSortParamString(Mockito.anyString());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void findByIdMethodTestWhenIdCorrect() {
        Event event = eventService.findById(id);

        assertEquals(eventOne, event);
    }

    @Test
    void findByIdMethodTestWhenIdNotCorrect() {
        assertThrows(ApplicationNotValidDataException.class, () -> eventService.findById(notCorrectId));
    }

    @Test
    void findByIdMethodTestWhenIdNotExist() {
        Mockito.doReturn(null).when(eventDaoMock).findById(Mockito.anyLong());

        assertThrows(ApplicationNotFoundException.class, () -> eventService.findById(id));
    }

    @Test
    void findByParametersMethodTestWhenParamMapEmpty() {
        List<Event> eventList = eventService.findByParameters(requestParamMap);

        assertEquals(events, eventList);
    }

    @Test
    void findByParametersMethodTestWhenParamsNotCorrect() {
        requestParamMap = Map.of(TOPIC_PARAM, "<topic>", DATE_PARAM, "not date", ORGANIZER_PARAM, "121", SORT_PARAM, "desc");
        List<Event> eventList = eventService.findByParameters(requestParamMap);

        assertTrue(eventList.isEmpty());
    }

    @Test
    void findByParametersMethodTestWhenAllParametersExist() {
        requestParamMap = Map.of(TOPIC_PARAM, eventTopic, DATE_PARAM, eventDate, ORGANIZER_PARAM, organizerName, SORT_PARAM, sort);
        List<Event> expected = List.of(eventOne);
        List<Event> result = eventService.findByParameters(requestParamMap);

        assertEquals(expected, result);
    }

    @Test
    void findByParametersMethodTestWhenTopicParamNull() {
        requestParamMap = Map.of(DATE_PARAM, eventDate, ORGANIZER_PARAM, organizerName, SORT_PARAM, sort);
        List<Event> expected = List.of(eventOne);
        List<Event> result = eventService.findByParameters(requestParamMap);

        assertEquals(expected, result);
    }

    @Test
    void findByParametersMethodTestWhenTopicAndDateParamsNull() {
        requestParamMap = Map.of(ORGANIZER_PARAM, organizerName, SORT_PARAM, sort);

        List<Event> expected = List.of(eventOne);
        List<Event> result = eventService.findByParameters(requestParamMap);

        assertEquals(expected, result);
    }

    @Test
    void deleteMethodTest() {
        assertDoesNotThrow(() -> eventService.delete(id));
    }

    @Test
    void updateEventMethodTest() {
        Event result = eventService.updateEvent(eventDto, id);

        assertEquals(eventOne, result);
    }

    @Test
    void createEventMethodTest() {
        Mockito.doReturn(eventTwo).when(eventDaoMock).insertEvent(Mockito.any(Event.class));
        Mockito.doReturn(List.of()).when(eventDaoMock).findEventByDate(Mockito.any(LocalDateTime.class), Mockito.any());

        Event result = eventService.createEvent(eventDto);

        assertEquals(eventTwo, result);
    }

    @Test
    void createEventMethodTestWhenDuplicateExist() {
        Mockito.doReturn(List.of(eventOne)).when(eventDaoMock)
                .findEventByDate(Mockito.any(LocalDateTime.class), Mockito.any());

        assertThrows(ApplicationDuplicateException.class, () -> eventService.createEvent(eventDto));
    }
}