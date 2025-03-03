package com.events.photo.services.impl;

import com.events.photo.exceptions.BadRequestException;
import com.events.photo.exceptions.NotFoundException;
import com.events.photo.models.entities.Event;
import com.events.photo.models.entities.User;
import com.events.photo.repositories.EventRepository;
import com.events.photo.services.EventService;
import com.events.photo.services.QrCodeGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;
  private final QrCodeGeneratorService qrCodeGeneratorService;
  private final AuthenticcationHelper authenticationHelper;

  public Event createEvent(Event event) throws BadRequestException {
    if (event.getStartDate().isAfter(event.getEndDate())) {
      throw new BadRequestException("A data de início deve ser anterior à data de término.");
    }

    if (eventRepository.existsByName(event.getName())) {
      throw new BadRequestException("Já existe um evento com este nome.");
    }

    User authenticatedUser = authenticationHelper.getAuthenticatedUser();
    event.setOrganizers(List.of(authenticatedUser));

    Event savedEvent = eventRepository.save(event);

    String qrCode = qrCodeGeneratorService.generateQrCode(savedEvent.getId().toString());
    savedEvent.setQrCode(qrCode);

    return eventRepository.save(savedEvent);
  }

  public Event getEventByQrCode(String qrCode) throws NotFoundException {
    User authenticatedUser = authenticationHelper.getAuthenticatedUser();
    Event foundEvent =
        eventRepository
            .findByQrCode(qrCode)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format("No event found with qrCode: '%s'.", qrCode)));
    if (!foundEvent.getOrganizers().contains(authenticatedUser)) {
      throw new NotFoundException(String.format("No event found with qrCode: '%s'.", qrCode));
    } else {
      return foundEvent;
    }
  }
}
