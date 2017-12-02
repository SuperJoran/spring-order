package be.jorandeboever.controllers;

import be.jorandeboever.domain.Event;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String welcome() {
        return "redirect:home" ;
    }

    @RequestMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("home", "event", new Event());
    }
}
