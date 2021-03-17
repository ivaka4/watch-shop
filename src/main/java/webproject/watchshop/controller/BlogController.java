package webproject.watchshop.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import webproject.watchshop.model.binding.ProductCategoryBindingModel;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.model.view.UserViewModel;
import webproject.watchshop.service.BlogService;
import webproject.watchshop.service.UserService;
import webproject.watchshop.util.Tools;


@Controller
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;
    private final ModelMapper modelMapper;
    private final Tools tools;
    private final UserService userService;

    public BlogController(BlogService blogService, ModelMapper modelMapper, Tools tools, UserService userService) {
        this.blogService = blogService;
        this.modelMapper = modelMapper;
        this.tools = tools;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView blog() {
        return new ModelAndView("blog");
    }

    @GetMapping("/details")
    public ModelAndView blogDetails() {
        return new ModelAndView("blog-details");
    }
    @GetMapping("/category/add")
    public ModelAndView blogCategoryAdd(Model model) {
        ModelAndView modelAndView = new ModelAndView("blog-category-add");
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
}
