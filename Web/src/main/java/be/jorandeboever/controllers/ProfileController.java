package be.jorandeboever.controllers;

import be.jorandeboever.services.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@Transactional
public class ProfileController {

    private final EventService eventService;

    public ProfileController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping("/profile")
    public ModelAndView profilePage(Principal principal) {
        return new ModelAndView("profile", "events", this.eventService.findByOwnerUsername(principal.getName()));
    }
}
