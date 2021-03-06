package webproject.watchshop.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import webproject.watchshop.exceptions.userEx.UserCannotSaveException;
import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.service.ProductService;
import webproject.watchshop.service.UserService;
import webproject.watchshop.util.Tools;

@Controller
public class CartController extends BaseController{
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final Tools tools;

    public CartController(ProductService productService, ModelMapper modelMapper, UserService userService, Tools tools) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.tools = tools;
    }

    @GetMapping("/cart")
    public ModelAndView cart(){
        return new ModelAndView("cart");
    }

    @GetMapping("/add-to-cart/{id}")
    public ModelAndView offerDetails(@PathVariable Long id) throws UserCannotSaveException {
        ProductServiceModel productServiceModel = productService.getProductBy(id);
        userService.addToCart(this.tools.getLoggedUser(), productServiceModel);

        return super.redirect("/cart");
    }
}
