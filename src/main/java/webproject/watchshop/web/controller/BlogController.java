package webproject.watchshop.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webproject.watchshop.model.binding.BlogCategoryAddBinding;
import webproject.watchshop.model.service.BlogCategoryServiceModel;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.model.view.UserViewModel;
import webproject.watchshop.service.BlogCategoryService;
import webproject.watchshop.service.BlogService;
import webproject.watchshop.service.UserService;
import webproject.watchshop.util.PageTitle;
import webproject.watchshop.util.Tools;

import javax.validation.Valid;


@Controller
@RequestMapping("/blog")
public class BlogController extends BaseController{
    private final BlogService blogService;
    private final BlogCategoryService blogCategoryService;
    private final ModelMapper modelMapper;
    private final Tools tools;
    private final UserService userService;

    public BlogController(BlogService blogService, BlogCategoryService blogCategoryService, ModelMapper modelMapper, Tools tools, UserService userService) {
        this.blogService = blogService;
        this.blogCategoryService = blogCategoryService;
        this.modelMapper = modelMapper;
        this.tools = tools;
        this.userService = userService;
    }

    private UserViewModel userProfile() {
        UserServiceModel userServiceModel = this.userService.findByUsername(this.tools.getLoggedUser());
        return this.modelMapper.map(userServiceModel, UserViewModel.class);
    }

    @PageTitle(name = "Title")
    @GetMapping
    public ModelAndView blog() {
        return new ModelAndView("blog");
    }

    @PageTitle(name = "Blog Details")
    @GetMapping("/details")
    public ModelAndView blogDetails() {
        return new ModelAndView("blog-details");
    }

    @PageTitle(name = "Blog category add")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/category/add")
    public ModelAndView blogCategoryAdd(Model model) {
        ModelAndView modelAndView = new ModelAndView("blog-category-add");
        UserViewModel userViewModel = userProfile();
        modelAndView.addObject("userUpdate", userViewModel);
        if (!model.containsAttribute("blogCategoryBindingModel")) {
            modelAndView.addObject("blogCategoryBindingModel", new BlogCategoryAddBinding());
        }
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/category/add")
    public ModelAndView blogCategoryAddConfirm(@Valid @ModelAttribute BlogCategoryAddBinding blogCategoryAddBinding,
                                               BindingResult bindingResult,
                                               RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("blogCategoryAddBinding", blogCategoryAddBinding);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.blogCategoryAddBinding", bindingResult);
            return super.redirect("/blog/category/add");
        }
        this.blogCategoryService.addBlogCategory(this.modelMapper.map(blogCategoryAddBinding, BlogCategoryServiceModel.class));

        return super.redirect("/blog");
    }
}
