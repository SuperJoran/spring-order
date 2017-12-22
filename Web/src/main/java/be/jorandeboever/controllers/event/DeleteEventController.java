package be.jorandeboever.controllers.event;

import be.jorandeboever.controllers.ProfileController;
import be.jorandeboever.services.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Transactional
public class DeleteEventController {

    private final EventService eventService;

    public DeleteEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping("/event/{eventName}/delete")
    public ModelAndView deleteEvent(@PathVariable String eventName) {
        this.eventService.deleteByName(eventName);
        return ProfileController.redirectToProfile();
    }
}
