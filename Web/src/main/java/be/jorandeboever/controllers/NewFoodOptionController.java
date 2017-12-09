package be.jorandeboever.controllers;

import be.jorandeboever.domain.ExtraOption;
import be.jorandeboever.domain.FoodOption;
import be.jorandeboever.domain.FoodOptionConfiguration;
import be.jorandeboever.domain.Size;
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
        return new ModelAndView("redirect:/event/" + eventName + "/" + configName + "/add");
    }

    @GetMapping("/event/{eventName}/{configName}")
    public ModelAndView foodOveriew(@PathVariable String configName, @PathVariable String eventName) {
        return new ModelAndView("add_food", "configuration", this.foodOptionConfigurationService.findByEventNameAndName(eventName, configName));
    }

    @GetMapping("/event/{eventName}/{configName}/add")
    public ModelAndView foodForm(@PathVariable String configName, @PathVariable String eventName) {
        ModelAndView modelAndView = new ModelAndView("add_food", "configuration", this.foodOptionConfigurationService.findByEventNameAndName(eventName, configName));
        modelAndView.addObject("food", new FoodOption());
        return modelAndView;
    }

    @PostMapping("/event/{eventName}/{configName}/add")
    public ModelAndView foodSubmit(@PathVariable String configName, @ModelAttribute FoodOption foodOption, @PathVariable String eventName) {
        FoodOptionConfiguration foodOptionConfiguration = this.foodOptionConfigurationService.findByEventNameAndName(eventName, configName);
        foodOption.setConfiguration(foodOptionConfiguration);
        foodOptionConfiguration.addFoodOption(foodOption);
        this.foodOptionConfigurationService.createOrUpdate(foodOptionConfiguration);

        return redirectToAddFood(eventName, configName);
    }

    @GetMapping("/event/{eventName}/{configName}/{foodName}/edit")
    public ModelAndView editFoodOption(
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
    public ModelAndView addExtra(
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

    @PostMapping("/event/{eventName}/{configName}/{foodName}/add_size")
    public ModelAndView addPrice(
            @PathVariable String configName,
            @PathVariable String eventName,
            @PathVariable String foodName,
            @ModelAttribute Size newSize
    ) {
        FoodOptionConfiguration foodOptionConfiguration = this.foodOptionConfigurationService.findByEventNameAndName(eventName, configName);

        this.getFoodOption(foodName, foodOptionConfiguration)
                .ifPresent(option -> {
                    newSize.setFoodOption(option);
                    option.addSize(newSize);
                });

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
