package be.jorandeboever.dao;

import be.jorandeboever.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventDao extends JpaRepository<Event, String> {

    Event findByName(String name);

    List<Event> findByOwnerUsername(String username);
}
