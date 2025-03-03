package com.events.photo.services;

import com.events.photo.exceptions.BadRequestException;
import com.events.photo.exceptions.NotFoundException;
import com.events.photo.models.entities.Event;

public interface EventService {

    Event createEvent(Event event) throws BadRequestException, NotFoundException;

    Event getEventByQrCode(String qrCode) throws NotFoundException;
}
