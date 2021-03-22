package webproject.watchshop.controller;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webproject.watchshop.enums.RoleEnum;
import webproject.watchshop.exceptions.CustomBaseException;
import webproject.watchshop.exceptions.addressEx.AddressIsNotExistException;
import webproject.watchshop.exceptions.userEx.UserCannotSaveException;
import webproject.watchshop.exceptions.userEx.UserRegistrationException;
import webproject.watchshop.model.binding.UserRegisterBindingModel;
import webproject.watchshop.model.binding.UserUpdateProfileBindingModel;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.model.view.UserViewModel;
import webproject.watchshop.service.UserService;
import webproject.watchshop.util.Tools;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Tools tools;

    public UserController(UserService userService, ModelMapper modelMapper, Tools tools) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.tools = tools;
    }

    @GetMapping("/login")
    public ModelAndView login(@AuthenticationPrincipal UserDetails user) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView profile(Model model) {
        ModelAndView modelAndView = new ModelAndView("profile");
        loggedUserInfo(modelAndView);
        return modelAndView;
    }

    private void loggedUserInfo(ModelAndView modelAndView) {
        UserServiceModel userServiceModel = this.userService.findByUsername(this.tools.getLoggedUser());
        UserViewModel userViewModel = this.modelMapper.map(userServiceModel, UserViewModel.class);
        modelAndView.addObject("userUpdate", userViewModel);
    }

    @PostMapping("/update")
    public ModelAndView profileUpdate(@Valid @ModelAttribute UserUpdateProfileBindingModel userUpdateProfileBindingModel,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors() || !userUpdateProfileBindingModel.getPassword()
                .equals(userUpdateProfileBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userUpdate", userUpdateProfileBindingModel);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterModel", bindingResult);
            return super.redirect("/users/profile");
        }
        UserServiceModel userServiceModel = this.modelMapper.map(userUpdateProfileBindingModel, UserServiceModel.class);
        userServiceModel.setUsername(this.tools.getLoggedUser());
        this.userService.updateProfile(userServiceModel);
        return super.redirect("/users/profile");
    }

    @GetMapping("/register")
    public ModelAndView register(Model model) {
        ModelAndView modelAndView = new ModelAndView("register");
        if (!model.containsAttribute("userRegisterModel")) {
            modelAndView.addObject("userRegisterModel", new UserRegisterBindingModel());
        }
        return modelAndView;
    }

    @PostMapping("/register")
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
        return super.redirect("/users/login");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/roles/add")
    public ModelAndView changeRole(){
        ModelAndView modelAndView = new ModelAndView("change-role");
        modelAndView.addObject("users", userService.getAllUsers());
        loggedUserInfo(modelAndView);
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/roles/add")
    public ModelAndView changeRoleConfirm(@RequestParam String username, @RequestParam String role){
        userService.changeRole(username, RoleEnum.valueOf(role.toUpperCase()));
        return super.redirect("/users/profile");
    }


}
