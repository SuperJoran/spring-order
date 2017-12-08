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
        newConfiguration.setEvent(event);

        this.foodOptionConfigurationDao.save(newConfiguration);
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
}
