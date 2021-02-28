package webproject.watchshop.util;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import webproject.watchshop.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniqueGitAddressValidator implements ConstraintValidator<UniqueEmail, String> {

    UserService userService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String gitAddress, ConstraintValidatorContext constraintValidatorContext) {
        return userService.emailExist(gitAddress);
    }
}
