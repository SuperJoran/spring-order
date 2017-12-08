package be.jorandeboever.dao;

import be.jorandeboever.domain.FoodOptionConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOptionConfigurationDao extends JpaRepository<FoodOptionConfiguration, String>{
    List<FoodOptionConfiguration> findAllByEvent_Owner_Username(String username);

    FoodOptionConfiguration findByEventNameAndName(String eventName, String name);

    void deleteByEventNameAndName(String eventName, String name);

    void deleteFoodOptionConfigurationByEventNameAndName(String eventName, String name);

    List<FoodOptionConfiguration> findAllByEventName(String eventName);
}
