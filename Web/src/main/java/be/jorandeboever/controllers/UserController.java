package be.jorandeboever.controllers;

import be.jorandeboever.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {
    private final PersonService personService;

    @Autowired
    public UserController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping("/all")
    public ModelAndView users() {
        return new ModelAndView("users", "users", this.personService.findAll());
    }
}
