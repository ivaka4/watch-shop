package webproject.watchshop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {

    @GetMapping("/")
    public ModelAndView index(@AuthenticationPrincipal
    UserDetails user) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("user", user != null ?
                user.getUsername() :
                "Anonymous");
        return modelAndView;
    }


}
