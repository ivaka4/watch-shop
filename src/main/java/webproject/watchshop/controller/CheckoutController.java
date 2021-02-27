package webproject.watchshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CheckoutController {

    @GetMapping("/checkout")
    public ModelAndView checkout(){
        return new ModelAndView("checkout");
    }
}
