package webproject.watchshop.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import webproject.watchshop.exceptions.userEx.UserCannotSaveException;
import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.model.view.ProductViewModel;
import webproject.watchshop.service.ProductService;
import webproject.watchshop.service.UserService;
import webproject.watchshop.util.Tools;

@Controller
public class ShopController extends BaseController{
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ShopController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/shop")
    public ModelAndView shop(){
        ModelAndView modelAndView = new ModelAndView("shop");
        modelAndView.addObject("allProducts", productService.getAllProducts());
        return modelAndView;
    }

}
