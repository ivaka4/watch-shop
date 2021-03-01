package webproject.watchshop.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webproject.watchshop.model.binding.ProductAddBindingModel;
import webproject.watchshop.model.service.ProductCategoryServiceModel;
import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.model.view.UserViewModel;
import webproject.watchshop.service.ProductCategoryService;
import webproject.watchshop.service.ProductService;
import webproject.watchshop.service.UserService;
import webproject.watchshop.util.Tools;

import javax.validation.Valid;

@Controller
public class ProductController extends BaseController{
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final ProductCategoryService productCategoryService;
    private final UserService userService;
    private final Tools tools;

    public ProductController(ProductService productService, ModelMapper modelMapper, ProductCategoryService productCategoryService, UserService userService, Tools tools) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.productCategoryService = productCategoryService;
        this.userService = userService;
        this.tools = tools;
    }

    @GetMapping("/products-details")
    public ModelAndView productsDetails() {
        return new ModelAndView("product_details");
    }

    @GetMapping("/add-product")
    public ModelAndView addProduct(Model model) {
        ModelAndView modelAndView = new ModelAndView("add-product");
        UserServiceModel userServiceModel = this.userService.findByUsername(this.tools.getLoggedUser());
        UserViewModel userViewModel = this.modelMapper.map(userServiceModel, UserViewModel.class);
        modelAndView.addObject("userUpdate", userViewModel);
        modelAndView.addObject("categories", productCategoryService.findAll());
        if (!model.containsAttribute("productAddBindingModel")) {
            modelAndView.addObject("productAddBindingModel", new ProductAddBindingModel());
        }
        return modelAndView;
    }

    @PostMapping("/add-product")
    public ModelAndView addProductConfirm(@Valid @ModelAttribute ProductAddBindingModel productAddBindingModel,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);
            return super.redirect("/add-product");
        }
        ProductServiceModel productServiceModel = this.modelMapper.map(productAddBindingModel, ProductServiceModel.class);
        this.productService.uploadProduct(productServiceModel);
        return super.redirect("/shop");
    }
}