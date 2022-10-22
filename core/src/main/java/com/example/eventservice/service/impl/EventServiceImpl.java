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
import com.example.eventservice.exception.ApplicationDuplicateException;
import com.example.eventservice.exception.ApplicationNotFoundException;
import com.example.eventservice.exception.ApplicationNotValidDataException;
import com.example.eventservice.service.EventService;
import com.example.eventservice.util.entitybuilder.EntityBuilder;
import com.example.eventservice.util.validator.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.eventservice.dao.ParamNames.*;
import static com.example.eventservice.exception.ErrorMessages.*;
import static com.example.eventservice.util.StringDateParser.parseStringToDate;

@Service
public class EventServiceImpl implements EventService {
    private static final Logger logger = LogManager.getLogger();
    private final EventDao eventDao;
    private final OrganizerDao organizerDao;
    private final AddressDao addressDao;
    private final DataValidator dataValidator;
    private final EntityBuilder<Event, EventDto> eventEntityBuilder;
    private final EntityBuilder<Organizer, OrganizerDto> organizerBuilder;
    private final EntityBuilder<Address, AddressDto> addressBuilder;

    @Autowired
    public EventServiceImpl(EventDao eventDao,
                            OrganizerDao organizerDao,
                            AddressDao addressDao,
                            DataValidator dataValidator,
                            EntityBuilder<Event, EventDto> eventEntityBuilder,
                            EntityBuilder<Organizer, OrganizerDto> organizerBuilder,
                            EntityBuilder<Address, AddressDto> addressBuilder) {
        this.eventDao = eventDao;
        this.organizerDao = organizerDao;
        this.addressDao = addressDao;
        this.dataValidator = dataValidator;
        this.eventEntityBuilder = eventEntityBuilder;
        this.organizerBuilder = organizerBuilder;
        this.addressBuilder = addressBuilder;
    }

    @Override
    public Event findById(long id) {
        if (!dataValidator.isNumberValid(id)) {
            logger.error(NOT_CORRECT_ID);
            throw new ApplicationNotValidDataException(NOT_CORRECT_ID, id);
        }

        return Optional.ofNullable(eventDao.findById(id)).orElseThrow(() -> {
            logger.error(NOT_FOUND_EVENT);
            throw new ApplicationNotFoundException(NOT_FOUND_EVENT, id);
        });
    }

    @Override
    public List<Event> findByParameters(Map<String, String> requestParams) {
        List<Event> events;

        Map<String, String> searchParams = requestParams.entrySet()
                .stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        String eventTopic = searchParams.get(TOPIC);
        String eventTime = searchParams.get(TIME);
        String eventOrganizerName = searchParams.get(ORGANIZER);
        String sortParams = searchParams.get(SORT);



        if (eventTopic != null && dataValidator.isEventTopicValid(eventTopic)) {
            events = eventDao.findEventByTopic(eventTopic, null);

            if (eventTime != null && dataValidator.isEventDateValid(eventTime)) {
                events = filterEventsByTime(events, eventTime);
            }
            if (eventOrganizerName != null && dataValidator.isNameValid(eventOrganizerName)) {
                events = filterEventsByOrganizerName(events, eventOrganizerName);
            }
        } else if (eventTime != null && dataValidator.isEventDateValid(eventTime)) {
            events = eventDao.findEventByDate(parseStringToDate(eventTime), null);

            if (eventOrganizerName != null && dataValidator.isNameValid(eventOrganizerName)) {
                events = filterEventsByOrganizerName(events, eventOrganizerName);
            }
        } else  if (eventOrganizerName != null && dataValidator.isNameValid(eventOrganizerName)) {
            events = eventDao.findEventByOrganizerName(eventOrganizerName, null);
        } else {
            return eventDao.findAll(null);
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
        Event event = eventEntityBuilder.buildEntityFromDto(eventDto);

        if (isTimeLeft(event.getEventDate())) {
            logger.error(TIME_LEFT);
            throw new ApplicationNotValidDataException(TIME_LEFT, event.getEventDate());
        }

        if (isPlaceAlreadyTaken(event.getAddress(), event.getEventDate())) {
            logger.error(PLACE_TAKEN);
            throw new ApplicationDuplicateException(PLACE_TAKEN, event.getAddress());
        }

        if (isAlreadyExist(event)) {
            logger.error(EVENT_ALREADY_EXISTS);
            throw new ApplicationDuplicateException(EVENT_ALREADY_EXISTS, event);
        }

        return eventDao.insertEvent(event);
    }

    private boolean isPlaceAlreadyTaken(Address eventAddress, LocalDateTime eventTime) {
        List<Event> events = eventDao.findEventByDate(eventTime, null);

        return events.stream()
                .anyMatch(e -> e.getAddress().equals(eventAddress));
    }

    private boolean isTimeLeft(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now());
    }

    private boolean isAlreadyExist(Event event) {
        List<Event> events = eventDao.findEventByTopic(event.getTopic(), null);

        return events.stream()
                .anyMatch(e -> e.equals(event));
    }

    private Event buildUpdatedEvent(Event current, EventDto eventDto) {
        Map<String, Object> paramsForUpdate = buildEventParamsMapForUpdate(eventDto);

        for (var entry : paramsForUpdate.entrySet()) {
            String paramName = entry.getKey();
            Object paramValue = entry.getValue();

            if (paramName.equals(TOPIC) && !current.getTopic().equals(paramValue)) {
                current.setTopic(paramValue.toString());
            } else if (paramName.equals(DESCRIPTION) && !current.getDescription().equals(paramValue)) {
                current.setDescription(paramValue.toString());
            } else if (paramName.equals(TIME)) {
                LocalDateTime eventDate = parseStringToDate(paramValue.toString());
                if (!isTimeLeft(eventDate) && current.getEventDate().compareTo(eventDate) != 0) {
                    current.setEventDate(eventDate);
                }
            } else if (paramName.equals(ORGANIZERS)) {
                Set<Organizer> organizers = ((List<OrganizerDto>) paramValue).stream()
                        .map(organizerBuilder::buildEntityFromDto)
                        .map(this::findTheSameOrganizer)
                        .collect(Collectors.toSet());

                current.setOrganizers(organizers);
            } else if (paramName.equals(ADDRESS)) {
                AddressDto addressDto = (AddressDto) paramValue;
                Address address = findTheSameAddress(addressBuilder.buildEntityFromDto(addressDto));

                current.setAddress(address);
            }
        }
        return current;
    }

    private Map<String, Object> buildEventParamsMapForUpdate(EventDto eventDto) {
        Map<String, Object> paramsForUpdate = new HashMap<>();

        if (eventDto.getTopic() != null) {
            paramsForUpdate.put(TOPIC, eventDto.getTopic());
        }

        if (eventDto.getDescription() != null) {
            paramsForUpdate.put(DESCRIPTION, eventDto.getDescription());
        }

        if (eventDto.getEventDate() != null) {
            paramsForUpdate.put(TIME, eventDto.getEventDate());
        }

        if (!eventDto.getOrganizers().isEmpty()) {
            paramsForUpdate.put(ORGANIZERS, eventDto.getOrganizers());
        }

        if (eventDto.getAddress() != null) {
            paramsForUpdate.put(ADDRESS, eventDto.getAddress());
        }

        Map<String, Object> wrongParams = buildWrongParamsMap(paramsForUpdate);

        if (!wrongParams.isEmpty()) {
            logger.error(NOT_CORRECT_EVENT_DATA + wrongParams);
            throw new ApplicationNotValidDataException(NOT_CORRECT_EVENT_DATA, wrongParams);
        }

        return paramsForUpdate;
    }

    private Map<String, Object> buildWrongParamsMap(Map<String, Object> params) {
        Map<String, Object> wrongParams = new HashMap<>();

        for (var entry : params.entrySet()) {
            String paramName = entry.getKey();
            Object paramValue = entry.getValue();

            if (paramName.equals(TOPIC) && !dataValidator.isEventTopicValid(paramValue.toString())) {
                wrongParams.put(paramName, paramValue);
            } else if (paramName.equals(DESCRIPTION) && !dataValidator.isEventDescriptionValid(paramValue.toString())) {
                wrongParams.put(paramName, paramValue);
            } else if (paramName.equals(TIME) && !dataValidator.isEventDateValid(paramValue.toString())) {
                wrongParams.put(paramName, paramValue);
            } else if (paramName.equals(ADDRESS) && !dataValidator.isAddressValid((AddressDto) paramValue)) {
                wrongParams.put(paramName, paramValue);
            } else if (paramName.equals(ORGANIZERS)) {
                List<OrganizerDto> organizers = (List<OrganizerDto>) paramValue;
                List<OrganizerDto> notCorrectOrganizers = organizers.stream()
                        .filter(org -> !dataValidator.isOrganizerValid(org))
                        .toList();
                if (!notCorrectOrganizers.isEmpty()) {
                    wrongParams.put(paramName, notCorrectOrganizers);
                }
            }
        }
        return wrongParams;
    }

    private Organizer findTheSameOrganizer(Organizer organizer) {
        List<Organizer> organizers = organizerDao.findOrganizerByName(organizer.getName());

        return organizers.stream()
                .filter(org -> org.equals(organizer))
                .findFirst()
                .orElse(organizer);
    }

    private Address findTheSameAddress(Address address) {
        List<Address> addresses = addressDao.findAddressByCity(address.getCity());

        return addresses.stream()
                .filter(addr -> addr.equals(address))
                .findFirst()
                .orElse(address);
    }

    private List<Event> filterEventsByTime(List<Event> events, String eventTime) {
        return events.stream()
                .filter(e -> e.getEventDate().compareTo(parseStringToDate(eventTime)) == 0)
                .toList();
    }

    private List<Event> filterEventsByOrganizerName(List<Event> events, String organizerName) {
        return events.stream()
                .filter(e -> e.getOrganizers()
                        .stream()
                        .anyMatch(org -> org.getName().equals(organizerName)))
                .toList();
    }
}