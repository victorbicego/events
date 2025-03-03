package com.events.photo.controllers;

import static com.events.photo.controllers.ControllerUtilService.buildResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.events.photo.exceptions.BadRequestException;
import com.events.photo.exceptions.NotFoundException;
import com.events.photo.models.ApiResponse;
import com.events.photo.models.entities.Event;
import com.events.photo.services.EventService;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
@Validated
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<ApiResponse<Event>> createEvent(@RequestBody Event event) throws BadRequestException,
            NotFoundException {

        Event createdEvent = eventService.createEvent(event);
        return buildResponse(HttpStatus.OK, createdEvent, "Event created.");
    }

    @GetMapping("/{qrCode}")
    public ResponseEntity<ApiResponse<Event>> getEventByQrCode(@PathVariable String qrCode) throws NotFoundException {

        Event foundEvent = eventService.getEventByQrCode(qrCode);
        return buildResponse(HttpStatus.OK, foundEvent, "Event found.");
    }
}
