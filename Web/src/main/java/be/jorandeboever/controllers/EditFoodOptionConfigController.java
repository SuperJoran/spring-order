package be.jorandeboever.controllers;

import be.jorandeboever.domain.FoodOptionConfiguration;
import be.jorandeboever.services.FoodOptionConfigurationService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Transactional
public class EditFoodOptionConfigController {

    private final FoodOptionConfigurationService foodOptionConfigurationService;

    public EditFoodOptionConfigController(FoodOptionConfigurationService foodOptionConfigurationService) {
        this.foodOptionConfigurationService = foodOptionConfigurationService;
    }

    @PostMapping("/event/{eventName}/{configName}/change_name")
    public ModelAndView changeConfigName(@PathVariable String eventName, @PathVariable String configName, @ModelAttribute FoodOptionConfiguration foodOptionConfiguration) {
        FoodOptionConfiguration configurationToUpdate = this.foodOptionConfigurationService.findByEventNameAndName(eventName, configName);

        configurationToUpdate.setName(foodOptionConfiguration.getName());

        this.foodOptionConfigurationService.createOrUpdate(configurationToUpdate);

        return EventController.redirectToEvent(eventName);
    }
}
