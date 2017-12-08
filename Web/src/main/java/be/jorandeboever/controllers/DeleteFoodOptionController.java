package be.jorandeboever.controllers;

import be.jorandeboever.domain.FoodOptionConfiguration;
import be.jorandeboever.services.FoodOptionConfigurationService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@Transactional
public class DeleteFoodOptionController {

    private final FoodOptionConfigurationService foodOptionConfigurationService;

    public DeleteFoodOptionController(FoodOptionConfigurationService foodOptionConfigurationService) {
        this.foodOptionConfigurationService = foodOptionConfigurationService;
    }

    @PostMapping("/event/{eventName}/{configName}/{foodName}/delete")
    public ModelAndView deleteFoodConfiguration(
            HttpServletRequest httpServletRequest,
            @PathVariable String eventName, @PathVariable String configName, @PathVariable String foodName) {
        FoodOptionConfiguration configuration = this.foodOptionConfigurationService.findByEventNameAndName(eventName, configName);
        configuration.getFoodOptions().removeIf(foodOption -> foodName.equals(foodOption.getName()));
        this.foodOptionConfigurationService.createOrUpdate(configuration);

        String referer = httpServletRequest.getHeader("Referer");
        return new ModelAndView("redirect:"+ referer);
    }
}
