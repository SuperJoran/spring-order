package be.jorandeboever.controllers.event;

import be.jorandeboever.domain.Event;
import be.jorandeboever.domain.FoodOptionConfiguration;
import be.jorandeboever.services.EventService;
import be.jorandeboever.services.FoodOptionConfigurationService;
import be.jorandeboever.services.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@Transactional
public class AddEventController {
    private final EventService eventService;
    private final PersonService personService;
    private final FoodOptionConfigurationService foodOptionConfigurationService;

    public AddEventController(EventService eventService, PersonService personService, FoodOptionConfigurationService foodOptionConfigurationService) {
        this.eventService = eventService;
        this.personService = personService;
        this.foodOptionConfigurationService = foodOptionConfigurationService;
    }

    @GetMapping("/new_event")
    public ModelAndView eventForm() {
        return new ModelAndView("add_event", "event", new Event());
    }

    @PostMapping("/new_event")
    public String eventSubmit(@ModelAttribute Event event, Principal principal) {
        event.setDateTime(LocalDateTime.now());
        event.setOwner(this.personService.findByUsername(principal.getName()));
        Event result = this.eventService.saveOrUpdate(event);
        this.foodOptionConfigurationService.createOrUpdate(new FoodOptionConfiguration(result));

        return "redirect:event/" + event.getName();
    }
}
