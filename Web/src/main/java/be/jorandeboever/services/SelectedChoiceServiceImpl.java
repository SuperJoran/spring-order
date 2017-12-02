package be.jorandeboever.services;

import be.jorandeboever.dao.SelectedChoiceDao;
import be.jorandeboever.domain.Event;
import be.jorandeboever.domain.Person;
import be.jorandeboever.domain.SelectedChoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SelectedChoiceServiceImpl implements SelectedChoiceService {
    private final SelectedChoiceDao selectedChoiceDao;

    private final EventService eventService;
    private final PersonService personService;

    @Autowired
    public SelectedChoiceServiceImpl(SelectedChoiceDao selectedChoiceDao, EventService eventService, PersonService personService) {
        this.selectedChoiceDao = selectedChoiceDao;
        this.eventService = eventService;
        this.personService = personService;
    }

    @Override
    public SelectedChoice createOrUpdate(SelectedChoice selectedChoice) {
        return this.selectedChoiceDao.save(selectedChoice);
    }

    @Override
    public SelectedChoice createOrUpdate(String eventName, String foodUuid, String username) {
        Event event = this.eventService.findByName(eventName);
        SelectedChoice selectedChoice = new SelectedChoice((Person) this.personService.loadUserByUsername(username));

        selectedChoice.setFoodOption(
                event.getFoodOptionConfiguration().getFoodOptions().stream()
                        .filter(foodOption -> foodOption.getUuid().equals(foodUuid))
                        .findFirst().orElse(null)
        );

        return this.createOrUpdate(selectedChoice);
    }

    @Override
    public List<SelectedChoice> findByEventName(String eventName) {
        return this.selectedChoiceDao.findByEventName(eventName);
    }

    @Override
    public void deleteAllByPersonUsernameAndEventName(String username, String eventName) {
        this.selectedChoiceDao.deleteAllByPerson_UsernameAndFoodOption_Configuration_Event_Name(username, eventName);
    }
}
