package be.jorandeboever.dao;

import be.jorandeboever.domain.FoodOptionConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodOptionConfigurationDao extends JpaRepository<FoodOptionConfiguration, String>{
    List<FoodOptionConfiguration> findAllByEvent_Owner_Username(String username);
}
