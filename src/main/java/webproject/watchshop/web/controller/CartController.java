package webproject.watchshop.web.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import webproject.watchshop.exceptions.userEx.UserCannotSaveException;
import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.model.view.ProductViewModel;
import webproject.watchshop.service.ProductService;
import webproject.watchshop.service.UserService;
import webproject.watchshop.util.annotation.PageTitle;
import webproject.watchshop.util.Tools;

import java.util.List;

@Controller
public class CartController extends BaseController {
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

    @PageTitle(name = "Cart")
    @GetMapping("/cart")
    public ModelAndView cart() {
        ModelAndView modelAndView = new ModelAndView("cart");
        List<ProductViewModel> productViewModels = this
                .modelMapper
                .map(this.userService.findByUsername(tools.getLoggedUser()).getCart(),
                        new TypeToken<List<ProductViewModel>>() {
                        }.getType());
        modelAndView.addObject("products", productViewModels);
        double totalPrice = getTotalPrice(productViewModels);
        modelAndView.addObject("totalPrice", totalPrice);
        return modelAndView;
    }

    @GetMapping("/add-to-cart/{id}")
    public ModelAndView addToCartById(@PathVariable Long id) throws UserCannotSaveException {
        ProductServiceModel productServiceModel = productService.getProductBy(id);
        userService.addToCart(this.tools.getLoggedUser(), productServiceModel);

        return super.redirect("/cart");
    }

    private double getTotalPrice(List<ProductViewModel> buyedProducts) {
        double totalPrice = 0.00;
        for (ProductViewModel buyedProduct : buyedProducts) {
            if (buyedProduct.getPrice() != null) {
                totalPrice += buyedProduct.getPrice().doubleValue();
            }
        }
        return totalPrice;
    }

    @GetMapping("/cart/remove/all")
    public ModelAndView clearCart() {
        this.userService.removeAllProducts(tools.getLoggedUser());
        return super.redirect("/cart");
    }
    @GetMapping("/remove-from-cart/{id}")
    public ModelAndView removeFromCart(@PathVariable("id") Long id) {
      ModelAndView modelAndView = new ModelAndView("cart");
      this.userService.removeSingleProduct(id, this.tools.getLoggedUser());
      return super.redirect("/cart");
    }

}
