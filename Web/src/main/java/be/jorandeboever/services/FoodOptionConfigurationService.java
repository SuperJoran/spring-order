package be.jorandeboever.services;

import be.jorandeboever.domain.FoodOptionConfiguration;

import java.util.List;

public interface FoodOptionConfigurationService {

    List<FoodOptionConfiguration> findAllByEventOwnerUsername(String username);

    void copyFoodOptionConfiguration(String eventName, String foodOptionConfigurationUuid);

    FoodOptionConfiguration findByUuid(String uuid);

    FoodOptionConfiguration findByEventNameAndName(String eventName, String name);

    FoodOptionConfiguration createOrUpdate(FoodOptionConfiguration foodOptionConfiguration);
}
