package be.jorandeboever.services;

import be.jorandeboever.dao.EventDao;
import be.jorandeboever.domain.Event;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EventServiceImpl implements EventService {
    private final EventDao eventDao;

    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public Event saveOrUpdate(Event event) {
        StopWatch sw = new StopWatch();
        sw.start();
        List<Event> someEvents = IntStream.range(0, 10)
                .mapToObj(i -> new Event(LocalDateTime.now(), String.format("Random-%s", new Random().nextDouble()), event.getOwner()))
                .collect(Collectors.toList());

        this.eventDao.save(someEvents);

        sw.stop();
        System.out.println(sw.prettyPrint());
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

    @Override
    public void deleteByName(String eventName) {
        this.eventDao.deleteByName(eventName);
    }

    @Override
    public void removeAll(Iterable<Event> events) {
        this.eventDao.delete(events);
    }
}
