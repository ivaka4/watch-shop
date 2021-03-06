package webproject.watchshop.model.view;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webproject.watchshop.model.service.AuthorityServiceModel;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserViewModel {

    private String username;
    private String firstName;
    private String lastName;
    private String email;

    private String profilePicture;
    private AddressViewModel address;
    private Integer phone;
    private List<ProductViewModel> cart;
    private Set<AuthorityServiceModel> authorities;


}
