package webproject.watchshop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import webproject.watchshop.util.annotation.PageTitle;

@Controller
public class ContactController {

    @PageTitle(name = "Contact")
    @GetMapping("/contact")
    public ModelAndView contact(){
        return new ModelAndView("contact");
    }
}
