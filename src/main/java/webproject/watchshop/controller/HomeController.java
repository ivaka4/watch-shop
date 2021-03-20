package webproject.watchshop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import webproject.watchshop.model.view.ProductViewModel;
import webproject.watchshop.service.ProductService;

import java.util.List;

@Controller
public class HomeController extends BaseController {
    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ModelAndView index(@AuthenticationPrincipal
    UserDetails user) {
        ModelAndView modelAndView = new ModelAndView("index");
        List<ProductViewModel> productViewModelList = this.productService.getLastThreeProduct();
        modelAndView.addObject("latestProducts", productViewModelList);
        return modelAndView;
    }


}
