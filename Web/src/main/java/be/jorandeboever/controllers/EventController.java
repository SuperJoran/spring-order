package be.jorandeboever.controllers;

import be.jorandeboever.domain.Event;
import be.jorandeboever.services.EventService;
import be.jorandeboever.services.PersonChoicesSearchResultService;
import be.jorandeboever.services.SelectedChoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Transactional
public class EventController {

    private final EventService eventService;
    private final PersonChoicesSearchResultService personChoicesSearchResultService;
    private final SelectedChoiceService selectedChoiceService;

    //TODO: split controller in multiple controllers
    public EventController(
            EventService eventService,
            PersonChoicesSearchResultService personChoicesSearchResultService,
            SelectedChoiceService selectedChoiceService
    ) {
        this.eventService = eventService;
        this.personChoicesSearchResultService = personChoicesSearchResultService;
        this.selectedChoiceService = selectedChoiceService;
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
        modelAndView.addObject("participants", this.personChoicesSearchResultService.findParticipantsByEventName(eventName));
        return modelAndView;
    }

    @PostMapping("/event/{eventName}/food/{foodName}/add_user")
    public ModelAndView simpleUserAdd(
            @PathVariable String eventName,
            @PathVariable String foodName,
            @RequestParam String username
    ) {
        Event event = this.eventService.findByName(eventName);
        event.getFoodOptionConfiguration().getFoodOptions().stream()
                .filter(o -> o.getName().equals(foodName))
                .findFirst()
                .ifPresent(o -> o.addSimpleUser(username));

        this.eventService.saveOrUpdate(event);

        return redirectToEvent(eventName);
    }

    @RequestMapping("/event/{eventName}/remove/{username}")
    public ModelAndView simpleUserRemove(
            @PathVariable String eventName,
            @PathVariable String username
    ) {
        Event event = this.eventService.findByName(eventName);
        event.getFoodOptionConfiguration().getFoodOptions()
                .forEach(o -> o.removeSimpleUser(username));

        this.eventService.saveOrUpdate(event);

        this.selectedChoiceService.deleteAllByPersonUsernameAndEventName(username, eventName);

        return redirectToEvent(event.getName());
    }
}
