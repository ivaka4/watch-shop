package webproject.watchshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlogController {

    @GetMapping("/blog")
    public ModelAndView blog() {
        return new ModelAndView("blog");
    }

    @GetMapping("/blog-details")
    public ModelAndView blogDetails() {
        return new ModelAndView("blog-details");
    }
}
