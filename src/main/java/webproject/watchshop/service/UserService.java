package webproject.watchshop.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.model.view.UserViewModel;

public interface UserService extends UserDetailsService {

    UserServiceModel register(UserServiceModel userServiceModel);

    boolean emailExist(String email);

    boolean userExists(String username);

    UserServiceModel findByUsername(String loggedUser);

    UserServiceModel updateProfile(UserServiceModel userServiceModel);
}
