package webproject.watchshop.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import webproject.watchshop.enums.RoleEnum;
import webproject.watchshop.exceptions.userEx.UserCannotSaveException;
import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.model.view.UserViewModel;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel register(UserServiceModel userServiceModel);

    boolean emailExist(String email);

    boolean userExists(String username);

    UserServiceModel findByUsername(String loggedUser);

    UserServiceModel updateProfile(UserServiceModel userServiceModel);

    List<UserViewModel> getAllUsers();

    UserServiceModel changeRole(String username, RoleEnum authority) throws UserCannotSaveException;

    boolean addToCart(String username,ProductServiceModel productServiceModel) throws UserCannotSaveException;

    boolean removeAllProducts(String loggedUser);

    boolean removeSingleProduct(Long id, String loggedUser);
}
