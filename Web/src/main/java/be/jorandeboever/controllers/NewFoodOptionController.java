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

    private static ModelAndView redirectToAddFood(@PathVariable String configUuid) {
        return new ModelAndView("redirect:/event/" + configUuid + "/add_food");
    }

    @GetMapping("/event/{configUuid}/add_food")
    public ModelAndView foodForm(@PathVariable String configUuid) {
        ModelAndView modelAndView = new ModelAndView("add_food", "configuration", this.foodOptionConfigurationService.findByUuid(configUuid));
        modelAndView.addObject("food", new FoodOption());
        modelAndView.addObject("extra", new ExtraOption());
        return modelAndView;
    }

    @PostMapping("/event/{configUuid}/add_food")
    public ModelAndView foodSubmit(@PathVariable String configUuid, @ModelAttribute FoodOption foodOption) {
        FoodOptionConfiguration foodOptionConfiguration = this.foodOptionConfigurationService.findByUuid(configUuid);
        foodOption.setConfiguration(foodOptionConfiguration);
        foodOptionConfiguration.addFoodOption(foodOption);
        this.foodOptionConfigurationService.createOrUpdate(foodOptionConfiguration);

        return redirectToAddFood(configUuid);
    }

    @GetMapping("/event/{configUuid}/food/{foodOptionUuid}/add_extra")
    public ModelAndView extraForm(
            @PathVariable String configUuid,
            @PathVariable String foodOptionUuid
    ) {
        FoodOptionConfiguration config = this.foodOptionConfigurationService.findByUuid(configUuid);
        ModelAndView modelAndView = new ModelAndView("add_food", "configuration", config);
        modelAndView.addObject("food", this.getFoodOption(foodOptionUuid, config).orElse(null));
        modelAndView.addObject("extra", new ExtraOption());
        return modelAndView;
    }

    @PostMapping("/event/{configUuid}/food/{foodOptionUuid}/add_extra")
    public ModelAndView extraSubmit(
            @PathVariable String configUuid,
            @PathVariable String foodOptionUuid,
            @ModelAttribute ExtraOption extraOption
    ) {
        FoodOptionConfiguration foodOptionConfiguration = this.foodOptionConfigurationService.findByUuid(configUuid);
        this.getFoodOption(foodOptionUuid, foodOptionConfiguration)
                .ifPresent(o -> o.addExtraOption(extraOption));

        this.foodOptionConfigurationService.createOrUpdate(foodOptionConfiguration);
        return redirectToAddFood(configUuid);

    }

    private Optional<FoodOption> getFoodOption(String foodOptionUuid, FoodOptionConfiguration foodOptionConfiguration) {
        return foodOptionConfiguration.getFoodOptions()
                .stream()
                .filter(o -> o.getUuid().equals(foodOptionUuid))
                .findFirst();
    }
}
