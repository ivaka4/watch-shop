package webproject.watchshop.controller;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webproject.watchshop.model.binding.UserRegisterBindingModel;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users-login")
    public ModelAndView login(@AuthenticationPrincipal UserDetails user) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/users-profile")
    public ModelAndView profile(@AuthenticationPrincipal UserDetails user) {
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/users-register")
    public ModelAndView register(Model model) {
        ModelAndView modelAndView = new ModelAndView("register");
        if (!model.containsAttribute("userRegisterModel")) {
            modelAndView.addObject("userRegisterModel", new UserRegisterBindingModel());
        }
        return modelAndView;
    }

    @PostMapping("/users-register")
    public ModelAndView registerConfirm(@Valid @ModelAttribute UserRegisterBindingModel userRegisterModel,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || !userRegisterModel.getPassword().equals(userRegisterModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterModel", userRegisterModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterModel", bindingResult);
            return super.redirect("/users-register");
        }

        this.userService.register(this.modelMapper.map(userRegisterModel, UserServiceModel.class));
        return super.redirect("/users-login");
    }
}
