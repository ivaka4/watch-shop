package webproject.watchshop.model.binding;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import webproject.watchshop.util.UniqueEmail;
import webproject.watchshop.util.UniqueUsername;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
@Setter
public class UserRegisterBindingModel {

    @NotBlank
    @Length(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    @UniqueUsername
	private String username;
    @NotBlank
    @Length(min = 6, max = 30, message = "Password must be between 6 and 30")
    @Pattern(regexp = "^[a-zA-Z0-9-\\/.^&*_!@%+>)(]+$", message = "Password must contains, at least " +
            "1 special symbol, 1 uppercase," +
            " lowercase letter, and 1 digit")
    private String password;
    @NotBlank
    @Length(min = 6, max = 30, message = "Password must be between 6 and 30")
    @Pattern(regexp = "^[a-zA-Z0-9-\\/.^&*_!@%+>)(]+$", message = "Password must contains, at least " +
            "1 special symbol, 1 uppercase," +
            " lowercase letter, and 1 digit")
    private String confirmPassword;
    @NotBlank
    @UniqueEmail
    @Length(min = 5, max = 35, message = "Email must be at least 5 characters")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?" +
            "(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message = "Email is not valid")
    private String email;
    @NotBlank
    @Length(min = 3, max = 20, message = "First name must be between 3 and 20 characters")
    private String firstName;
    @NotBlank
    @Length(min = 3, max = 20, message = "Last name must be between 3 and 20 characters")
    private String lastName;
    @NotBlank
    @Length(max = 10, message = "Phone number must be 10 digits")
    private String phoneNumber;

    /* For Address */
    @NotBlank
    @Length(max = 10, message = "Post code cannot be more than 10 digits")
    private String postCode;
    @NotBlank
    @Length(min = 2, max = 20, message = "City must be between 2 and 20 characters")
    private String city;
    @NotBlank
    @Length(min = 2, max = 20, message = "Country must be between 2 and 20 characters")
    private String country;
    @NotBlank
    @Length(min = 2, max = 20, message = "Address must be between 2 and 20 characters")
    private String address1;
    @NotBlank
    @Length(min = 2, max = 20, message = "Address must be between 2 and 20 characters")
    private String address2;

    public UserRegisterBindingModel() {
    }

}
