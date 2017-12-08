package be.jorandeboever.services;

import be.jorandeboever.dao.FoodOptionConfigurationDao;
import be.jorandeboever.domain.Event;
import be.jorandeboever.domain.FoodOptionConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodOptionConfigurationServiceImpl implements FoodOptionConfigurationService{
    private final FoodOptionConfigurationDao foodOptionConfigurationDao;
    private final EventService eventService;

    public FoodOptionConfigurationServiceImpl(FoodOptionConfigurationDao foodOptionConfigurationDao, EventService eventService) {
        this.foodOptionConfigurationDao = foodOptionConfigurationDao;
        this.eventService = eventService;
    }

    @Override
    public List<FoodOptionConfiguration> findAllByEventOwnerUsername(String username) {
        return this.foodOptionConfigurationDao.findAllByEvent_Owner_Username(username);
    }

    @Override
    public void copyFoodOptionConfiguration(String eventName, String foodOptionConfigurationUuid) {
        FoodOptionConfiguration oldConfiguration = this.foodOptionConfigurationDao.findOne(foodOptionConfigurationUuid);
        FoodOptionConfiguration newConfiguration = new FoodOptionConfiguration(oldConfiguration);
        Event event = this.eventService.findByName(eventName);
        this.checkIfEventAlreadyContainsConfigWithSameName(oldConfiguration, newConfiguration, event);

        newConfiguration.setEvent(event);

        this.foodOptionConfigurationDao.save(newConfiguration);
    }

    private void checkIfEventAlreadyContainsConfigWithSameName(FoodOptionConfiguration oldConfiguration, FoodOptionConfiguration newConfiguration, Event event) {
        if(event.getFoodOptionConfigurations().stream().anyMatch(config -> config.getName().equals(newConfiguration.getName()))){
            newConfiguration.setName(String.format("%s (imported from %s)", newConfiguration.getName(), oldConfiguration.getEvent().getName()));
        }
    }

    @Override
    public FoodOptionConfiguration findByUuid(String uuid) {
        return this.foodOptionConfigurationDao.getOne(uuid);
    }

    @Override
    public FoodOptionConfiguration findByEventNameAndName(String eventName, String name) {
        return this.foodOptionConfigurationDao.findByEventNameAndName(eventName, name);
    }

    @Override
    public FoodOptionConfiguration createOrUpdate(FoodOptionConfiguration foodOptionConfiguration) {
        return this.foodOptionConfigurationDao.save(foodOptionConfiguration);
    }

    @Override
    public void deleteByEventNameAndName(String eventName, String name) {
       this.foodOptionConfigurationDao.deleteFoodOptionConfigurationByEventNameAndName(eventName, name);
    }

    @Override
    public List<FoodOptionConfiguration> findAllByEventName(String eventName) {
        return this.foodOptionConfigurationDao.findAllByEventName(eventName);
    }
}
