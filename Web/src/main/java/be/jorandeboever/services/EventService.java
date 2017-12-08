package be.jorandeboever.services;

import be.jorandeboever.domain.Event;

import java.util.List;

public interface EventService {

    Event saveOrUpdate(Event event);

    Event findByName(String name);

    List<Event> findByOwnerUsername(String username);

    void deleteByName(String eventName);
}
