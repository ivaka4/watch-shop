package webproject.watchshop.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webproject.watchshop.model.binding.ProductAddBindingModel;
import webproject.watchshop.model.binding.ProductEditBindingModel;
import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.model.view.ProductViewModel;
import webproject.watchshop.model.view.UserViewModel;
import webproject.watchshop.service.ProductCategoryService;
import webproject.watchshop.service.ProductService;
import webproject.watchshop.service.UserService;
import webproject.watchshop.util.PageTitle;
import webproject.watchshop.util.Tools;

import javax.validation.Valid;
import java.io.IOException;

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

    @PageTitle(name = "Product Add")
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
    public ModelAndView addProductConfirm(@Valid @ModelAttribute("productAddBindingModel") ProductAddBindingModel productAddBindingModel,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) throws IOException {
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

    @PageTitle(name = "Product Details")
    @GetMapping("/details/{id}")
    public ModelAndView offerDetails(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("product_details");

        ProductViewModel productViewModel = this.modelMapper
                .map(productService.getProductBy(id), ProductViewModel.class);

        modelAndView.addObject("product", productViewModel);

        return modelAndView;
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") Long id) {
        this.productService.removeProduct(id);
        return super.redirect("/shop");
    }

    @PageTitle(name = "Product edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable("id") Long id, Model model) {
        ModelAndView modelAndView = new ModelAndView("edit-product");
        UserViewModel userViewModel = userProfile();
        modelAndView.addObject("userUpdate", userViewModel);
        ProductViewModel productViewModel = this.modelMapper.map(productService.getProductBy(id), ProductViewModel.class);
        modelAndView.addObject("categories", productCategoryService.findAll());
        modelAndView.addObject("productEditModel", productViewModel);
        if (!model.containsAttribute("productEditBindingModel")) {
            modelAndView.addObject("productEditBindingModel", new ProductEditBindingModel());
        }
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @PostMapping("/edit")
    public ModelAndView productUpdate(@ModelAttribute("productEditBindingModel") ProductEditBindingModel productEditBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productEditBindingModel", productEditBindingModel);
            redirectAttributes.addFlashAttribute("productEditBindingModel", productEditBindingModel);
            return super.redirect("/product/update");
        }

        ProductServiceModel psm = this.modelMapper.map(productEditBindingModel, ProductServiceModel.class);
        this.productService.editProduct(psm);
        return super.redirect("/shop");
    }

}
