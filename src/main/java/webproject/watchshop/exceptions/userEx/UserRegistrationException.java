package webproject.watchshop.exceptions.userEx;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import webproject.watchshop.exceptions.CustomBaseException;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "User registration exception!")
public class UserRegistrationException extends CustomBaseException {

	public UserRegistrationException(String msg) {
        super(msg);
    }
}
