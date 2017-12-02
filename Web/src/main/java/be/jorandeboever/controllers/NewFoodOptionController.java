package be.jorandeboever.controllers;

import be.jorandeboever.domain.FoodOption;
import be.jorandeboever.domain.FoodOptionConfiguration;
import be.jorandeboever.services.FoodOptionConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@Transactional
public class NewFoodOptionController {
    private final FoodOptionConfigurationService foodOptionConfigurationService;

    @Autowired
    public NewFoodOptionController(FoodOptionConfigurationService foodOptionConfigurationService) {
        this.foodOptionConfigurationService = foodOptionConfigurationService;
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
}
