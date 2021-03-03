package webproject.watchshop.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import webproject.watchshop.service.ProductService;

@Controller
public class ShopController {
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
