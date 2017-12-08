package be.jorandeboever.controllers;

import be.jorandeboever.domain.Event;
import be.jorandeboever.domain.FoodOptionConfiguration;
import be.jorandeboever.services.EventService;
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

    public AddEventController(EventService eventService, PersonService personService) {
        this.eventService = eventService;
        this.personService = personService;
    }

    @GetMapping("/new_event")
    public ModelAndView eventForm() {
        return new ModelAndView("add_event", "event", new Event());
    }

    @PostMapping("/new_event")
    public String eventSubmit(@ModelAttribute Event event, Principal principal) {
        event.setDateTime(LocalDateTime.now());
        event.setOwner(this.personService.findByUsername(principal.getName()));
        event.addFoodOptionConfiguration(new FoodOptionConfiguration(event));
        this.eventService.saveOrUpdate(event);

        return "redirect:event/" + event.getName();
    }
}
