package webproject.watchshop.controller;

import org.modelmapper.ModelMapper;
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
import webproject.watchshop.model.binding.ProductCategoryBindingModel;
import webproject.watchshop.model.service.ProductCategoryServiceModel;
import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.model.view.UserViewModel;
import webproject.watchshop.service.ProductCategoryService;
import webproject.watchshop.service.UserService;
import webproject.watchshop.util.Tools;

import javax.validation.Valid;

@Controller
@RequestMapping("/category")
public class ProductCategoryController extends BaseController {
    private final ProductCategoryService productCategoryService;
    private final ModelMapper modelMapper;
    private final Tools tools;
    private final UserService userService;

    public ProductCategoryController(ProductCategoryService productCategoryService, ModelMapper modelMapper, Tools tools, UserService userService) {
        this.productCategoryService = productCategoryService;
        this.modelMapper = modelMapper;
        this.tools = tools;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add")
    public ModelAndView addProduct(Model model) {
        ModelAndView modelAndView = new ModelAndView("add-product_category");
        UserViewModel userViewModel = userProfile();
        modelAndView.addObject("userUpdate", userViewModel);
        if (!model.containsAttribute("productCategoryBindingModel")) {
            modelAndView.addObject("productCategoryBindingModel", new ProductCategoryBindingModel());
        }
        return modelAndView;
    }

    private UserViewModel userProfile() {
        UserServiceModel userServiceModel = this.userService.findByUsername(this.tools.getLoggedUser());
        return this.modelMapper.map(userServiceModel, UserViewModel.class);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ModelAndView addProductConfirm(@Valid @ModelAttribute ProductCategoryBindingModel productCategoryBindingModel,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productCategoryBindingModel", productCategoryBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.productCategoryBindingModel", bindingResult);
            return super.redirect("/category/add");
        }
        ProductCategoryServiceModel productCategoryServiceModel = this.modelMapper
                .map(productCategoryBindingModel, ProductCategoryServiceModel.class);
        this.productCategoryService.addCategory(productCategoryServiceModel);
        return super.redirect("/product/add");
    }


}
