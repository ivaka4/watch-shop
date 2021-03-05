package webproject.watchshop.controller;

import jdk.jfr.ContentType;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webproject.watchshop.exceptions.CustomBaseException;
import webproject.watchshop.exceptions.addressEx.AddressIsNotExistException;
import webproject.watchshop.exceptions.userEx.UserCannotSaveException;
import webproject.watchshop.exceptions.userEx.UserRegistrationException;
import webproject.watchshop.model.binding.ProductAddBindingModel;
import webproject.watchshop.model.service.ProductCategoryServiceModel;
import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.model.view.ProductViewModel;
import webproject.watchshop.model.view.UserViewModel;
import webproject.watchshop.service.ProductCategoryService;
import webproject.watchshop.service.ProductService;
import webproject.watchshop.service.UserService;
import webproject.watchshop.util.Tools;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add")
    public ModelAndView addProduct(Model model) {
        ModelAndView modelAndView = new ModelAndView("add-product");
        UserViewModel userViewModel = userProfile();
        modelAndView.addObject("userUpdate", userViewModel);
        modelAndView.addObject("categories", productCategoryService.findAll());
        if (!model.containsAttribute("productAddBindingModel")) {
            modelAndView.addObject("productAddBindingModel", new ProductAddBindingModel());
        }
        return modelAndView;
    }

    private UserViewModel userProfile() {
        UserServiceModel userServiceModel = this.userService.findByUsername(this.tools.getLoggedUser());
        return this.modelMapper.map(userServiceModel, UserViewModel.class);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ModelAndView addProductConfirm(@Valid @ModelAttribute ProductAddBindingModel productAddBindingModel,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);
            return super.redirect("/product/add");
        }
        ProductServiceModel productServiceModel = this.modelMapper.map(productAddBindingModel, ProductServiceModel.class);
        this.productService.uploadProduct(productServiceModel);
        return super.redirect("/shop");
    }

    @GetMapping(name = "/details")
    public ModelAndView productDetails() {
        return new ModelAndView("product_details");
    }

    @GetMapping("/details/{id}")
    public ModelAndView offerDetails(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("product_details");

        ProductViewModel productViewModel = this.modelMapper
                .map(productService.getProductBy(id).orElseThrow(), ProductViewModel.class);

        modelAndView.addObject("product", productViewModel);

        return modelAndView;
    }

}
