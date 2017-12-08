package be.jorandeboever.controllers;

import be.jorandeboever.domain.Event;
import be.jorandeboever.services.EventService;
import be.jorandeboever.services.FoodOptionConfigurationService;
import be.jorandeboever.services.PersonChoicesSearchResultService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Transactional
public class EventController {

    private final EventService eventService;
    private final PersonChoicesSearchResultService personChoicesSearchResultService;
    private final FoodOptionConfigurationService foodOptionConfigurationService;

    //TODO: split controller in multiple controllers
    public EventController(
            EventService eventService,
            PersonChoicesSearchResultService personChoicesSearchResultService,
            FoodOptionConfigurationService foodOptionConfigurationService
    ) {
        this.eventService = eventService;
        this.personChoicesSearchResultService = personChoicesSearchResultService;
        this.foodOptionConfigurationService = foodOptionConfigurationService;
    }


    @PostMapping("/search")
    public ModelAndView searchEvent(@ModelAttribute Event event) {
        //todo show error if event not found
        return redirectToEvent(event.getName());
    }

    public static ModelAndView redirectToEvent(String eventName) {
        return new ModelAndView("redirect:/event/" + eventName);
    }

    @GetMapping("/event/{eventName}")
    public ModelAndView overview(@PathVariable String eventName) {
        ModelAndView modelAndView = new ModelAndView("event", "event", this.eventService.findByName(eventName));
        modelAndView.addObject("foodOptionConfigurations", this.foodOptionConfigurationService.findAllByEventName(eventName));
        modelAndView.addObject("participants", this.personChoicesSearchResultService.findParticipantsByEventName(eventName));
        return modelAndView;
    }

}
