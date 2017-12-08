package be.jorandeboever.services;

import be.jorandeboever.dao.SelectedChoiceDao;
import be.jorandeboever.domain.FoodOptionConfiguration;
import be.jorandeboever.domain.Person;
import be.jorandeboever.domain.SelectedChoice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectedChoiceServiceImpl implements SelectedChoiceService {
    private final SelectedChoiceDao selectedChoiceDao;

    private final PersonService personService;
    private final FoodOptionConfigurationService foodOptionConfigurationService;

    public SelectedChoiceServiceImpl(SelectedChoiceDao selectedChoiceDao, PersonService personService, FoodOptionConfigurationService foodOptionConfigurationService) {
        this.selectedChoiceDao = selectedChoiceDao;
        this.personService = personService;
        this.foodOptionConfigurationService = foodOptionConfigurationService;
    }

    @Override
    public SelectedChoice createOrUpdate(SelectedChoice selectedChoice) {
        return this.selectedChoiceDao.save(selectedChoice);
    }

    @Override
    public SelectedChoice createSelectedOption(String eventName, String foodUuid, String username) {
        List<FoodOptionConfiguration> foodOptionConfigurations = this.foodOptionConfigurationService.findAllByEventName(eventName);
        SelectedChoice selectedChoice = new SelectedChoice((Person) this.personService.loadUserByUsername(username));

        selectedChoice.setFoodOption(
                foodOptionConfigurations.stream()
                        .flatMap(config -> config.getFoodOptions().stream())
                        .filter(foodOption -> foodOption.getUuid().equals(foodUuid))
                        .findFirst().orElse(null)
        );

        return this.createOrUpdate(selectedChoice);
    }

    @Override
    public SelectedChoice addExtraOption(String eventName, String foodUuid, String extraUuid, String username) {
        SelectedChoice selectedChoice = this.findByEventName(eventName).stream()
                .filter(c -> c.getPerson().getUsername().equals(username))
                .filter(c -> c.getFoodOption().getUuid().equals(foodUuid))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("You can only select extras for already selected food options."));

        selectedChoice.addExtraOption(selectedChoice.getFoodOption().getExtraOptions().stream()
                .filter(e -> e.getUuid().equals(extraUuid))
                .findFirst().orElse(null));

        return this.createOrUpdate(selectedChoice);
    }

    @Override
    public List<SelectedChoice> findByEventName(String eventName) {
        return this.selectedChoiceDao.findAllByFoodOption_Configuration_Event_Name(eventName);
    }

    @Override
    public void deleteAllByPersonUsernameAndEventName(String username, String eventName) {
        this.selectedChoiceDao.deleteAllByPerson_UsernameAndFoodOption_Configuration_Event_Name(username, eventName);
    }
}
