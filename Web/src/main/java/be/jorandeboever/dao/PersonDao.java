package be.jorandeboever.dao;

import be.jorandeboever.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao extends JpaRepository<Person, String> {
    void deleteByUsername(String username);
    Person findByUsername(String username);
}
