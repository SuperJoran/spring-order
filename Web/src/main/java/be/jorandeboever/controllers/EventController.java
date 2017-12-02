package be.jorandeboever.controllers;

import be.jorandeboever.domain.Event;
import be.jorandeboever.domain.FoodOption;
import be.jorandeboever.services.EventService;
import be.jorandeboever.services.PersonChoicesSearchResultService;
import be.jorandeboever.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class EventController {

    private final EventService eventService;
    private final PersonChoicesSearchResultService personChoicesSearchResultService;
    private final PersonService personService;

    @Autowired
    public EventController(EventService eventService, PersonChoicesSearchResultService personChoicesSearchResultService, PersonService personService) {
        this.eventService = eventService;
        this.personChoicesSearchResultService = personChoicesSearchResultService;
        this.personService = personService;
    }

    @GetMapping("/event")
    public ModelAndView eventForm() {
        return new ModelAndView("add_event", "event", new Event());
    }

    @PostMapping("/event")
    public String eventSubmit(@ModelAttribute Event event, Principal principal) {
        event.setDateTime(LocalDateTime.now());
        event.setOwner(this.personService.findByUsername(principal.getName()));
        this.eventService.saveOrUpdate(event);

        return "redirect:event/" + event.getName();
    }

    @PostMapping("/search")
    public String searchEvent(@ModelAttribute Event event) {
        //todo show error if event not found
        return "redirect:event/" + event.getName();
    }

    @GetMapping("/event/{eventName}")
    public ModelAndView overview(@PathVariable("eventName") String eventName) {
        ModelAndView modelAndView = new ModelAndView("event", "event", this.eventService.findByName(eventName));
        modelAndView.addObject("participants", this.personChoicesSearchResultService.findParticipantsByEventName(eventName));
        return modelAndView;
    }

    @GetMapping("/event/{eventName}/add_food")
    public ModelAndView foodForm(@PathVariable("eventName") String eventName) {
        ModelAndView modelAndView = new ModelAndView("add_food", "event", this.eventService.findByName(eventName));
        modelAndView.addObject("food", new FoodOption());
        return modelAndView;
    }

    @PostMapping("/event/{eventName}/add_food")
    public ModelAndView foodSubmit(@PathVariable("eventName") String eventName, @ModelAttribute FoodOption foodOption) {
        Event event = this.eventService.findByName(eventName);
        event.getFoodOptionConfiguration().addFoodOption(foodOption);
        ModelAndView modelAndView = new ModelAndView("add_food", "event", this.eventService.saveOrUpdate(event));
        modelAndView.addObject("food", new FoodOption());

        return modelAndView;
    }

    @PostMapping("/event/{eventName}/food/{foodName}/add_user")
    public ModelAndView simpleUserSubmit(
            @PathVariable("eventName") String eventName,
            @PathVariable("foodName") String foodName,
            @RequestParam("username") String username
    ) {
        Event event = this.eventService.findByName(eventName);
        event.getFoodOptionConfiguration().getFoodOptions().stream()
                .filter(o -> o.getName().equals(foodName))
                .findFirst()
                .ifPresent(o -> o.addSimpleUser(username));

        this.eventService.saveOrUpdate(event);

        return new ModelAndView("redirect:/event/" + eventName);
    }
}
