package be.jorandeboever.controllers;

import be.jorandeboever.services.EventService;
import be.jorandeboever.services.FoodOptionConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


@Controller
@Transactional
public class ImportFoodConfigurationController {

    private final FoodOptionConfigurationService foodOptionConfigurationService;
    private final EventService eventService;

    @Autowired
    public ImportFoodConfigurationController(FoodOptionConfigurationService foodOptionConfigurationService, EventService eventService) {
        this.foodOptionConfigurationService = foodOptionConfigurationService;
        this.eventService = eventService;
    }

    @GetMapping("/event/{eventName}/import")
    public ModelAndView importConfiguration(@PathVariable("eventName") String eventName, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("import_food_config", "configurations", this.foodOptionConfigurationService.findAllByEventOwnerUsername(principal.getName()));
        modelAndView.addObject("event", this.eventService.findByName(eventName));
        return modelAndView;
    }

    @RequestMapping("/event/{eventName}/import/{configurationUuid}")
    public ModelAndView eventForm(@PathVariable("eventName") String eventName, @PathVariable("configurationUuid") String configurationUuid) {
        this.foodOptionConfigurationService.copyFoodOptionConfiguration(eventName, configurationUuid);

        return new ModelAndView("redirect:/event/" + eventName);
    }
}
