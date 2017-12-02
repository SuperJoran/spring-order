package be.jorandeboever.controllers;

import be.jorandeboever.domain.FoodOption;
import be.jorandeboever.domain.SelectedChoice;
import be.jorandeboever.services.EventService;
import be.jorandeboever.services.PersonService;
import be.jorandeboever.services.SelectedChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class InvitationController {

    private final EventService eventService;
    private final SelectedChoiceService selectedChoiceService;
    private final PersonService personService;

    @Autowired
    public InvitationController(EventService eventService, SelectedChoiceService selectedChoiceService, PersonService personService) {
        this.eventService = eventService;
        this.selectedChoiceService = selectedChoiceService;
        this.personService = personService;
    }

    @GetMapping("/event/{eventName}/invitation")
    public ModelAndView eventForm(@PathVariable("eventName") String eventName, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("invitation", "event", this.eventService.findByName(eventName));
        modelAndView.addObject("selectedFoodOptions", this.getAlreadySelectedFoodOptions(eventName, principal));

        return modelAndView;
    }

    private List<FoodOption> getAlreadySelectedFoodOptions(@PathVariable("eventName") String eventName, Principal principal) {
        return this.selectedChoiceService.findByEventName(eventName).stream()
                .filter(c -> c.getPerson().getUsername().equals(principal.getName()))
                .map(SelectedChoice::getFoodOption)
                .collect(Collectors.toList());
    }

    @GetMapping("/event/{eventName}/invitation/{foodUuid}")
    public ModelAndView acceptFoodOption(@PathVariable("eventName") String eventName, @PathVariable("foodUuid") String foodUuid, Principal principal) {

        this.selectedChoiceService.createOrUpdate(eventName, foodUuid, principal.getName());

        return new ModelAndView("redirect:/event/" + eventName + "/invitation");
    }
}
