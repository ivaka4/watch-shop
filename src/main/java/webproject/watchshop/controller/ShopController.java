package webproject.watchshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShopController {

    @GetMapping("/shop")
    public ModelAndView shop(){
        return new ModelAndView("shop");
    }
}
