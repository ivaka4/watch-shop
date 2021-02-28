package webproject.watchshop.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String phoneNumber;
    private List<ProductServiceModel> cart;

    private String postCode;
    private String city;
    private String country;
    private String address1;
    private String address2;

    private AddressViewModel address;
    private Set<AuthorityServiceModel> authorities;
}
