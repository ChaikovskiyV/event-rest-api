package com.example.eventservice.model.service.impl;

import com.example.eventservice.model.dao.AddressDao;
import com.example.eventservice.model.dao.EventDao;
import com.example.eventservice.model.dao.OrganizerDao;
import com.example.eventservice.model.dto.AddressDto;
import com.example.eventservice.model.dto.EventDto;
import com.example.eventservice.model.dto.OrganizerDto;
import com.example.eventservice.model.entity.Address;
import com.example.eventservice.model.entity.Event;
import com.example.eventservice.model.entity.Organizer;
import com.example.eventservice.model.entity.entityfactory.AddressEntityFactory;
import com.example.eventservice.model.entity.entityfactory.EntityFactory;
import com.example.eventservice.model.entity.entityfactory.EventEntityFactory;
import com.example.eventservice.model.entity.entityfactory.OrganizerEntityFactory;
import com.example.eventservice.exception.ApplicationDuplicateException;
import com.example.eventservice.exception.ApplicationNotFoundException;
import com.example.eventservice.exception.ApplicationNotValidDataException;
import com.example.eventservice.model.service.EventService;
import com.example.eventservice.model.util.querybuilder.EventSortQueryBuilder;
import com.example.eventservice.model.util.validator.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.eventservice.model.dao.ParamNames.*;
import static com.example.eventservice.exception.ErrorMessages.*;
import static com.example.eventservice.model.util.StringDateParser.parseStringToDate;

@Service
public class EventServiceImpl implements EventService {
    private final EventDao eventDao;
    private final OrganizerDao organizerDao;
    private final AddressDao addressDao;
    private final DataValidator dataValidator;
    private final EntityFactory<Event, EventDto> eventFactory;
    private final EntityFactory<Organizer, OrganizerDto> organizerFactory;
    private final EntityFactory<Address, AddressDto> addressFactory;
    private final EventSortQueryBuilder queryBuilder;

    @Autowired
    public EventServiceImpl(EventDao eventDao,
                            OrganizerDao organizerDao,
                            AddressDao addressDao,
                            DataValidator dataValidator,
                            EventEntityFactory eventFactory,
                            OrganizerEntityFactory organizerFactory,
                            AddressEntityFactory addressEntityFactory,
                            EventSortQueryBuilder queryBuilder) {
        this.eventDao = eventDao;
        this.organizerDao = organizerDao;
        this.addressDao = addressDao;
        this.dataValidator = dataValidator;
        this.eventFactory = eventFactory;
        this.organizerFactory = organizerFactory;
        this.addressFactory = addressEntityFactory;
        this.queryBuilder = queryBuilder;
    }

    @Override
    public Event findById(long id) {
        if (!dataValidator.isNumberValid(id)) {
            throw new ApplicationNotValidDataException(NOT_CORRECT_ID, id);
        }

        return Optional.ofNullable(eventDao.findById(id)).orElseThrow(() -> {
            throw new ApplicationNotFoundException(NOT_FOUND_EVENT, id);
        });
    }

    /**
     * Perform query to database and filter data depending on provided parameters
     * @param requestParams - Map with parameters. If it's empty, the findAll query will be sent to database.
     * @return List of Event objects that match request parameters.
     */
    @Override
    public List<Event> findByParameters(Map<String, String> requestParams) {
        List<Event> events = new ArrayList<>();

        String eventTopic = requestParams.get(EVENT_TOPIC);
        String eventTime = requestParams.get(EVENT_DATE);
        String eventOrganizerName = requestParams.get(ORGANIZER);
        String sortParams = queryBuilder.buildSortParamString(requestParams.get(SORT));

        if (eventTopic == null && eventTime == null && eventOrganizerName == null) {
            events = eventDao.findAll(sortParams);
        } else if (eventTopic != null && dataValidator.isEventTopicValid(eventTopic)) {
            events = eventDao.findEventByTopic(eventTopic, sortParams);
            events = filterEventsByTime(events, eventTime);
            events = filterEventsByOrganizerName(events, eventOrganizerName);
        } else if (eventTime != null && dataValidator.isEventDateValid(eventTime)) {
            events = eventDao.findEventByDate(parseStringToDate(eventTime), sortParams);
            events = filterEventsByOrganizerName(events, eventOrganizerName);
        } else if (eventOrganizerName != null && dataValidator.isNameValid(eventOrganizerName)) {
            events = eventDao.findEventByOrganizerName(eventOrganizerName, sortParams);
        }

        return events;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(long id) {
        Event event = findById(id);

        eventDao.delete(event);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Event updateEvent(EventDto eventDto, long id) {
        Event currentEvent = findById(id);
        Event updatedEvent = buildUpdatedEvent(currentEvent, eventDto);

        return eventDao.update(updatedEvent);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Event createEvent(EventDto eventDto) {
        Event event = eventFactory.buildEntityFromDto(eventDto);

        if (isTimeLeft(event.getEventDate())) {
            throw new ApplicationNotValidDataException(TIME_LEFT, event.getEventDate());
        }

        if (isPlaceAlreadyTaken(event.getAddress(), event.getEventDate())) {
            throw new ApplicationDuplicateException(PLACE_TAKEN, event.getAddress());
        }

        return eventDao.insertEvent(event);
    }

    /**
     * Check whether chosen address and data are busy.
     */
    private boolean isPlaceAlreadyTaken(Address eventAddress, LocalDateTime eventTime) {
        List<Event> events = eventDao.findEventByDate(eventTime, null);

        return events.stream()
                .anyMatch(e -> e.getAddress().equals(eventAddress));
    }

    private boolean isTimeLeft(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now());
    }

    /**
     * Replace changed fields in current event.
     */
    private Event buildUpdatedEvent(Event current, EventDto eventDto) {
        Map<String, Object> paramsForUpdate = buildEventParamsMapForUpdate(eventDto);

        for (var entry : paramsForUpdate.entrySet()) {
            String paramName = entry.getKey();
            Object paramValue = entry.getValue();

            if (paramName.equals(EVENT_TOPIC) && !current.getEventTopic().equals(paramValue)) {
                current.setEventTopic(paramValue.toString());
            } else if (paramName.equals(EVENT_DESCRIPTION) && !current.getEventDescription().equals(paramValue)) {
                current.setEventDescription(paramValue.toString());
            } else if (paramName.equals(EVENT_DATE)) {
                LocalDateTime eventDate = parseStringToDate(paramValue.toString());
                if (!isTimeLeft(eventDate) && current.getEventDate().compareTo(eventDate) != 0) {
                    current.setEventDate(eventDate);
                }
            } else if (paramName.equals(ORGANIZER)) {
                OrganizerDto organizerDto = (OrganizerDto) paramValue;
                Organizer organizer = findTheSameOrganizer(organizerFactory.buildEntityFromDto(organizerDto));

                current.setOrganizer(organizer);
            } else if (paramName.equals(ADDRESS)) {
                AddressDto addressDto = (AddressDto) paramValue;
                Address address = findTheSameAddress(addressFactory.buildEntityFromDto(addressDto));

                current.setAddress(address);
            }
        }
        return current;
    }

    /**
     * Builds map with not null parameters for updating from dto.
     * @Throws ApplicationNotValidDataException - if not correct parameters are provided.
     */
    private Map<String, Object> buildEventParamsMapForUpdate(EventDto eventDto) {
        Map<String, Object> paramsForUpdate = new HashMap<>();

        if (eventDto.getEventTopic() != null) {
            paramsForUpdate.put(EVENT_TOPIC, eventDto.getEventTopic());
        }

        if (eventDto.getEventDescription() != null) {
            paramsForUpdate.put(EVENT_DESCRIPTION, eventDto.getEventDescription());
        }

        if (eventDto.getEventDate() != null) {
            paramsForUpdate.put(EVENT_DATE, eventDto.getEventDate());
        }

        if (eventDto.getOrganizer() != null) {
            paramsForUpdate.put(ORGANIZER, eventDto.getOrganizer());
        }

        if (eventDto.getAddress() != null) {
            paramsForUpdate.put(ADDRESS, eventDto.getAddress());
        }

        Map<String, Object> wrongParams = buildWrongParamsMap(paramsForUpdate);

        if (!wrongParams.isEmpty()) {
            throw new ApplicationNotValidDataException(NOT_CORRECT_EVENT_DATA, wrongParams);
        }

        return paramsForUpdate;
    }

    /**
     * Check parameters for updating and build map with not correct parameters.
     */
    private Map<String, Object> buildWrongParamsMap(Map<String, Object> params) {
        Map<String, Object> wrongParams = new HashMap<>();

        for (var entry : params.entrySet()) {
            String paramName = entry.getKey();
            Object paramValue = entry.getValue();

            if ((paramName.equals(EVENT_TOPIC) && !dataValidator.isEventTopicValid(paramValue.toString())) ||
                    (paramName.equals(EVENT_DESCRIPTION) && !dataValidator.isEventDescriptionValid(paramValue.toString())) ||
                    (paramName.equals(EVENT_DATE) && !dataValidator.isEventDateValid(paramValue.toString())) ||
                    (paramName.equals(ADDRESS) && !dataValidator.isAddressValid((AddressDto) paramValue)) ||
                    (paramName.equals(ORGANIZER) && !dataValidator.isOrganizerValid((OrganizerDto) paramValue))) {

                wrongParams.put(paramName, paramValue);
            }
        }
        return wrongParams;
    }

    /**
     * Check whether such a provided organizer already exists, and if so, return the found organizer.
     */
    private Organizer findTheSameOrganizer(Organizer organizer) {
        List<Organizer> organizers = organizerDao.findOrganizerByName(organizer.getOrganizerName());

        return organizers.stream()
                .filter(org -> org.equals(organizer))
                .findFirst()
                .orElse(organizer);
    }

    /**
     * Check whether such a provided address already exists, and if so, return the found address.
     */
    private Address findTheSameAddress(Address address) {
        List<Address> addresses = addressDao.findAddressByCity(address.getAddressCity());

        return addresses.stream()
                .filter(addr -> addr.equals(address))
                .findFirst()
                .orElse(address);
    }

    private List<Event> filterEventsByTime(List<Event> events, String eventTime) {
        return (eventTime == null || !dataValidator.isEventDateValid(eventTime))
                ? events
                : events.stream()
                .filter(e -> e.getEventDate().compareTo(parseStringToDate(eventTime)) == 0)
                .toList();
    }

    private List<Event> filterEventsByOrganizerName(List<Event> events, String organizerName) {
        return (organizerName == null || !dataValidator.isNameValid(organizerName))
                ? events
                : events.stream()
                .filter(e -> e.getOrganizer().getOrganizerName().equals(organizerName))
                .toList();
    }
}