package be.jorandeboever.controllers;

import be.jorandeboever.services.FoodOptionConfigurationService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Transactional
public class DeleteFoodConfigurationController {

    private final FoodOptionConfigurationService foodOptionConfigurationService;

    public DeleteFoodConfigurationController(FoodOptionConfigurationService foodOptionConfigurationService) {
        this.foodOptionConfigurationService = foodOptionConfigurationService;
    }

    @PostMapping("/event/{eventName}/{configName}/delete")
    public ModelAndView deleteFoodConfiguration(@PathVariable String eventName, @PathVariable String configName) {
        this.foodOptionConfigurationService.deleteByEventNameAndName(eventName, configName);

        return EventController.redirectToEvent(eventName);
    }
}
