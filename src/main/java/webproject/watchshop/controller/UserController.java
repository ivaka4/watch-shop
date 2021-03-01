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
import webproject.watchshop.model.binding.UserUpdateProfileBindingModel;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.model.view.UserViewModel;
import webproject.watchshop.service.UserService;
import webproject.watchshop.util.Tools;

import javax.validation.Valid;

@Controller
public class UserController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Tools tools;

    public UserController(UserService userService, ModelMapper modelMapper, Tools tools) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.tools = tools;
    }

    @GetMapping("/users-login")
    public ModelAndView login(@AuthenticationPrincipal UserDetails user) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/users-profile")
    public ModelAndView profile(Model model) {
        ModelAndView modelAndView = new ModelAndView("profile");
        UserServiceModel userServiceModel = this.userService.findByUsername(this.tools.getLoggedUser());
        UserViewModel userViewModel = this.modelMapper.map(userServiceModel, UserViewModel.class);
        modelAndView.addObject("userUpdate", userViewModel);
        return modelAndView;
    }

    @PostMapping("/users-update")
    public ModelAndView profileUpdate(@Valid @ModelAttribute UserUpdateProfileBindingModel userUpdateProfileBindingModel,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userUpdateProfileBindingModel.getPassword()
                .equals(userUpdateProfileBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userUpdate", userUpdateProfileBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterModel", bindingResult);
            return super.redirect("/users-profile");
        }
        UserServiceModel userServiceModel = this.modelMapper.map(userUpdateProfileBindingModel, UserServiceModel.class);
        userServiceModel.setUsername(this.tools.getLoggedUser());
        this.userService.updateProfile(userServiceModel);
        return super.redirect("/users-profile");
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
