package be.jorandeboever.controllers;

import be.jorandeboever.domain.Event;
import be.jorandeboever.domain.FoodOption;
import be.jorandeboever.domain.FoodOptionConfiguration;
import be.jorandeboever.services.EventService;
import be.jorandeboever.services.FoodOptionConfigurationService;
import be.jorandeboever.services.PersonChoicesSearchResultService;
import be.jorandeboever.services.PersonService;
import be.jorandeboever.services.SelectedChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@Transactional
public class EventController {

    private final EventService eventService;
    private final PersonChoicesSearchResultService personChoicesSearchResultService;
    private final PersonService personService;
    private final SelectedChoiceService selectedChoiceService;
    private final FoodOptionConfigurationService foodOptionConfigurationService;

    //TODO: split controller in multiple controllers
    @Autowired
    public EventController(
            EventService eventService,
            PersonChoicesSearchResultService personChoicesSearchResultService,
            PersonService personService,
            SelectedChoiceService selectedChoiceService,
            FoodOptionConfigurationService foodOptionConfigurationService) {
        this.eventService = eventService;
        this.personChoicesSearchResultService = personChoicesSearchResultService;
        this.personService = personService;
        this.selectedChoiceService = selectedChoiceService;
        this.foodOptionConfigurationService = foodOptionConfigurationService;
    }

    @GetMapping("/event")
    public ModelAndView eventForm() {
        return new ModelAndView("add_event", "event", new Event());
    }

    @PostMapping("/event")
    public String eventSubmit(@ModelAttribute Event event, Principal principal) {
        event.setDateTime(LocalDateTime.now());
        event.setOwner(this.personService.findByUsername(principal.getName()));
        event.addFoodOptionConfiguration(new FoodOptionConfiguration(event));
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

    @GetMapping("/event/{configUuid}/add_food")
    public ModelAndView foodForm(@PathVariable("configUuid") String configUuid) {
        ModelAndView modelAndView = new ModelAndView("add_food", "configuration", this.foodOptionConfigurationService.findByUuid(configUuid));
        modelAndView.addObject("food", new FoodOption());
        return modelAndView;
    }

    @PostMapping("/event/{configUuid}/add_food")
    public ModelAndView foodSubmit(@PathVariable("configUuid") String configUuid, @ModelAttribute FoodOption foodOption) {
        FoodOptionConfiguration foodOptionConfiguration = this.foodOptionConfigurationService.findByUuid(configUuid);
        foodOption.setConfiguration(foodOptionConfiguration);
        foodOptionConfiguration.addFoodOption(foodOption);
        this.foodOptionConfigurationService.createOrUpdate(foodOptionConfiguration);

        return new ModelAndView("redirect:/event/" + configUuid + "/add_food");
    }

    @PostMapping("/event/{eventName}/food/{foodName}/add_user")
    public ModelAndView simpleUserAdd(
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

    @RequestMapping("/event/{eventName}/remove/{username}")
    public ModelAndView simpleUserRemove(
            @PathVariable("eventName") String eventName,
            @PathVariable("username") String username
    ) {
        Event event = this.eventService.findByName(eventName);
        event.getFoodOptionConfiguration().getFoodOptions()
                .forEach(o -> o.removeSimpleUser(username));

        this.eventService.saveOrUpdate(event);

        this.selectedChoiceService.deleteAllByPersonUsernameAndEventName(username, eventName);

        return new ModelAndView("redirect:/event/" + eventName);
    }
}
