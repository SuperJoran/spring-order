package be.jorandeboever.services;

import be.jorandeboever.dao.EventDao;
import be.jorandeboever.domain.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventDao eventDao;

    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public Event saveOrUpdate(Event event) {
        return this.eventDao.save(event);
    }

    @Override
    public Event findByName(String name) {
        return this.eventDao.findByName(name);
    }

    @Override
    public List<Event> findByOwnerUsername(String username) {
        return this.eventDao.findByOwnerUsername(username);
    }
}
