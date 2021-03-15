package webproject.watchshop.exceptions.userEx;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import webproject.watchshop.exceptions.CustomBaseException;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "User cannot be saved!")
public class UserCannotSaveException extends CustomBaseException {

	public UserCannotSaveException(String msg) {
        super(msg);
    }
}
