package be.jorandeboever.services;

import be.jorandeboever.domain.Person;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.List;

public interface PersonService extends UserDetailsManager {

    Person getPerson(String uuid);

    List<Person> findAll();

    Person findByUsername(String username);
}
