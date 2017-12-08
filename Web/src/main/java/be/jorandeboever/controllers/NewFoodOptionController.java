package be.jorandeboever.controllers;

import be.jorandeboever.domain.ExtraOption;
import be.jorandeboever.domain.FoodOption;
import be.jorandeboever.domain.FoodOptionConfiguration;
import be.jorandeboever.services.FoodOptionConfigurationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.Optional;

@Controller
@Transactional
public class NewFoodOptionController {
    private final FoodOptionConfigurationService foodOptionConfigurationService;

    public NewFoodOptionController(FoodOptionConfigurationService foodOptionConfigurationService) {
        this.foodOptionConfigurationService = foodOptionConfigurationService;
    }

    private static ModelAndView redirectToAddFood(String eventName, String configName) {
        return new ModelAndView("redirect:/event/" + eventName + "/" + configName + "/add_food");
    }

    @GetMapping("/event/{eventName}/{configName}/add_food")
    public ModelAndView foodForm(@PathVariable String configName, @PathVariable String eventName) {
        ModelAndView modelAndView = new ModelAndView("add_food", "configuration", this.foodOptionConfigurationService.findByEventNameAndName(eventName, configName));
        modelAndView.addObject("food", new FoodOption());
        modelAndView.addObject("extra", new ExtraOption());
        return modelAndView;
    }

    @PostMapping("/event/{eventName}/{configName}/add_food")
    public ModelAndView foodSubmit(@PathVariable String configName, @ModelAttribute FoodOption foodOption, @PathVariable String eventName) {
        FoodOptionConfiguration foodOptionConfiguration = this.foodOptionConfigurationService.findByEventNameAndName(eventName, configName);
        foodOption.setConfiguration(foodOptionConfiguration);
        foodOptionConfiguration.addFoodOption(foodOption);
        this.foodOptionConfigurationService.createOrUpdate(foodOptionConfiguration);

        return redirectToAddFood(eventName, configName);
    }

    @GetMapping("/event/{eventName}/{configName}/{foodName}/add_extra")
    public ModelAndView extraForm(
            @PathVariable String configName,
            @PathVariable String eventName,
            @PathVariable String foodName
    ) {
        FoodOptionConfiguration config = this.foodOptionConfigurationService.findByEventNameAndName(eventName, configName);
        ModelAndView modelAndView = new ModelAndView("add_food", "configuration", config);
        modelAndView.addObject("food", this.getFoodOption(foodName, config).orElse(null));
        modelAndView.addObject("extra", new ExtraOption());
        return modelAndView;
    }

    @PostMapping("/event/{eventName}/{configName}/{foodName}/add_extra")
    public ModelAndView extraSubmit(
            @PathVariable String configName,
            @PathVariable String eventName,
            @PathVariable String foodName,
            @ModelAttribute ExtraOption extraOption
    ) {
        FoodOptionConfiguration foodOptionConfiguration = this.foodOptionConfigurationService.findByEventNameAndName(eventName, configName);
        this.getFoodOption(foodName, foodOptionConfiguration)
                .ifPresent(o -> o.addExtraOption(extraOption));

        this.foodOptionConfigurationService.createOrUpdate(foodOptionConfiguration);
        return redirectToAddFood(eventName, configName);

    }

    private Optional<FoodOption> getFoodOption(String foodName, FoodOptionConfiguration foodOptionConfiguration) {
        return foodOptionConfiguration.getFoodOptions()
                .stream()
                .filter(o -> o.getName().equals(foodName))
                .findFirst();
    }
}
