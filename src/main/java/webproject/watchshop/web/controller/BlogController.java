package webproject.watchshop.web.controller;

import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webproject.watchshop.model.binding.BlogAddBindingModel;
import webproject.watchshop.model.binding.BlogCategoryAddBinding;
import webproject.watchshop.model.service.BlogCategoryServiceModel;
import webproject.watchshop.model.service.BlogServiceModel;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.model.view.BlogViewModel;
import webproject.watchshop.model.view.ProductViewModel;
import webproject.watchshop.model.view.UserViewModel;
import webproject.watchshop.service.BlogCategoryService;
import webproject.watchshop.service.BlogService;
import webproject.watchshop.service.UserService;
import webproject.watchshop.util.annotation.PageTitle;
import webproject.watchshop.util.Tools;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


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

    @PageTitle(name = "Blog")
    @GetMapping
    public ModelAndView blog() {
        ModelAndView modelAndView = new ModelAndView("blog");
        modelAndView.addObject("categories", blogCategoryService.findAll());
        modelAndView.addObject("blogs", blogService.findAll());
        List<BlogViewModel> recentBlogs = new LinkedList<>(this.blogService.findAll());
        Collections.reverse(recentBlogs);
        modelAndView.addObject("recentBlogs", recentBlogs.stream().limit(4));
        return modelAndView;
    }

    @PageTitle(name = "Blog Details")
    @GetMapping("/details/{id}")
    public ModelAndView blogDetails(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("blog-details");
        modelAndView.addObject("categories", blogCategoryService.findAll());


        BlogViewModel blogViewModel = this.modelMapper
                .map(blogService.getBlogById(id), BlogViewModel.class);

        modelAndView.addObject("blog", blogViewModel);

        return modelAndView;
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

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView blogAdd(Model model){
        ModelAndView modelAndView = new ModelAndView("blog-add");
        UserViewModel userViewModel = userProfile();
        modelAndView.addObject("userUpdate", userViewModel);
        modelAndView.addObject("categories", blogCategoryService.findAll());
        if (!model.containsAttribute("blogAddBindingModel")) {
            modelAndView.addObject("blogAddBindingModel", new BlogAddBindingModel());
        }
        return modelAndView;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView blogAddConfirm(@Valid @ModelAttribute BlogAddBindingModel blogAddBindingModel,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("blogAddBindingModel", blogAddBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.blogAddBindingModel", bindingResult);
            return super.redirect("/blog/add");
        }

        BlogServiceModel blogServiceModel = this.modelMapper.map(blogAddBindingModel, BlogServiceModel.class);
        this.blogService.addBlog(blogServiceModel, this.tools.getLoggedUser());
        return super.redirect("/blog");
    }
}
