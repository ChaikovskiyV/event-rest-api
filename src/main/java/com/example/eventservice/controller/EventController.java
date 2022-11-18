package com.example.eventservice.controller;

import com.example.eventservice.exception.ApplicationNotValidDataException;
import com.example.eventservice.exception.ErrorMessages;
import com.example.eventservice.model.dto.EventDto;
import com.example.eventservice.model.entity.Event;
import com.example.eventservice.model.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.eventservice.model.dao.ParamNames.*;

@RestController
@RequestMapping("api/v1/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public HttpEntity<Event> registerEvent(@Valid @RequestBody EventDto eventDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApplicationNotValidDataException(ErrorMessages.NOT_CORRECT_EVENT_DATA, bindingResult);
        }

        Event event = eventService.createEvent(eventDto);

        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public HttpEntity<Event> findEventById(@PathVariable("id") long id) {
        Event event = eventService.findById(id);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping
    public List<Event> findEvents(@RequestParam(name = "eventTopic", required = false) String eventTopic,
                                  @RequestParam(name = "eventDate", required = false) String eventDate,
                                  @RequestParam(name = "organizer", required = false) String organizer,
                                  @RequestParam(name = "sort", required = false) String sort) {

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(EVENT_TOPIC, eventTopic);
        requestParams.put(EVENT_DATE, eventDate);
        requestParams.put(ORGANIZER, organizer);
        requestParams.put(SORT, sort);

        return eventService.findByParameters(requestParams);
    }

    @PutMapping("/{id}")
    public HttpEntity<Event> updateEvent(@PathVariable("id") long id,
                                         @RequestBody EventDto eventDto) {

        Event event = eventService.updateEvent(eventDto, id);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable("id") long id) {
        eventService.delete(id);
    }
}