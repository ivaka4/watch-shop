package webproject.watchshop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import webproject.watchshop.util.PageTitle;

@Controller
public class AboutController {

    @PageTitle(name = "About")
    @GetMapping("/about")
    public ModelAndView about(){
        return new ModelAndView("about");
    }
}
