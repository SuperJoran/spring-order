package be.jorandeboever.dao;

import be.jorandeboever.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventDao extends JpaRepository<Event, String>{

    Event findByName(String name);

    List<Event> findByOwnerUsername(String username);
}
