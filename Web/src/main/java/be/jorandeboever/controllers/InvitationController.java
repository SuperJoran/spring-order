package be.jorandeboever.controllers;

import be.jorandeboever.domain.ExtraOption;
import be.jorandeboever.domain.FoodOption;
import be.jorandeboever.domain.SelectedChoice;
import be.jorandeboever.services.EventService;
import be.jorandeboever.services.SelectedChoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Transactional
public class InvitationController {

    private final EventService eventService;
    private final SelectedChoiceService selectedChoiceService;

    public InvitationController(EventService eventService, SelectedChoiceService selectedChoiceService) {
        this.eventService = eventService;
        this.selectedChoiceService = selectedChoiceService;
    }

    private static ModelAndView redirectToInvitation(@PathVariable("eventName") String eventName) {
        return new ModelAndView("redirect:/event/" + eventName + "/invitation");
    }

    @GetMapping("/event/{eventName}/invitation")
    public ModelAndView eventForm(@PathVariable("eventName") String eventName, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("invitation", "event", this.eventService.findByName(eventName));
        List<SelectedChoice> selectedChoices = this.selectedChoiceService.findByEventName(eventName);
        modelAndView.addObject("selectedFoodOptions", this.getAlreadySelectedFoodOptions(selectedChoices, principal));
        modelAndView.addObject("selectedExtraOptions", this.getAlreadySelectedExtraOptions(selectedChoices, principal));
        return modelAndView;
    }

    private List<FoodOption> getAlreadySelectedFoodOptions(Collection<SelectedChoice> selectedChoices, Principal principal) {
        return selectedChoices.stream()
                .filter(c -> c.getPerson().getUsername().equals(principal.getName()))
                .map(SelectedChoice::getFoodOption)
                .collect(Collectors.toList());
    }

    private List<ExtraOption> getAlreadySelectedExtraOptions(Collection<SelectedChoice> selectedChoices, Principal principal) {
        return selectedChoices.stream()
                .filter(c -> c.getPerson().getUsername().equals(principal.getName()))
                .flatMap(c -> c.getExtraOptions().stream())
                .collect(Collectors.toList());
    }

    @GetMapping("/event/{eventName}/invitation/{foodUuid}")
    public ModelAndView acceptFoodOption(
            @PathVariable("eventName") String eventName,
            @PathVariable("foodUuid") String foodUuid,
            Principal principal
    ) {

        this.selectedChoiceService.createSelectedOption(eventName, foodUuid, principal.getName());

        return this.redirectToInvitation(eventName);
    }

    @GetMapping("/event/{eventName}/invitation/{foodUuid}/extra_option/{extraUuid}")
    public ModelAndView acceptExtraOption(
            @PathVariable("eventName") String eventName,
            @PathVariable("foodUuid") String foodUuid,
            @PathVariable("extraUuid") String extraUuid,
            Principal principal
    ) {

        this.selectedChoiceService.addExtraOption(eventName, foodUuid, extraUuid, principal.getName());

        return this.redirectToInvitation(eventName);
    }
}
