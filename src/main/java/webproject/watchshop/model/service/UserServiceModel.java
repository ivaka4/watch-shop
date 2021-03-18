package webproject.watchshop.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import webproject.watchshop.model.entity.Blog;
import webproject.watchshop.model.entity.Product;
import webproject.watchshop.model.view.AddressViewModel;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private MultipartFile picture;
    private String profilePicture;
    private List<Blog> blogs;
    private List<Product> cart;

    private String postCode;
    private String city;
    private String country;
    private String address1;
    private String address2;

    private AddressViewModel address;
    private Set<AuthorityServiceModel> authorities;
}
