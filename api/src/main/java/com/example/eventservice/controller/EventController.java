package com.example.eventservice.controller;

import com.example.eventservice.dto.EventDto;
import com.example.eventservice.entity.Event;
import com.example.eventservice.service.EventService;
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

import static com.example.eventservice.dao.ParamNames.*;

@RestController
@RequestMapping("api/v1/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public HttpEntity<Event> registerEvent(@Valid @RequestBody EventDto eventDto, BindingResult bindingResult) {
        Event event = eventService.createEvent(eventDto);

        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public HttpEntity<Event> findEventById(@PathVariable("id") long id) {
        Event event = eventService.findById(id);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping
    public List<Event> findEvents(@RequestParam(name = "topic", required = false) String topic,
                                  @RequestParam(name = "time", required = false) String time,
                                  @RequestParam(name = "organizer", required = false) String organizer,
                                  @RequestParam(name = "sort", required = false) String sort) {

        Map<String, String> searchParams = new HashMap<>();
        searchParams.put(TOPIC, topic);
        searchParams.put(TIME, time);
        searchParams.put(ORGANIZER, organizer);
        searchParams.put(SORT, sort);

        return eventService.findByParameters(searchParams);
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