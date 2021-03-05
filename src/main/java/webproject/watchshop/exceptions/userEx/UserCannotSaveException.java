package webproject.watchshop.exceptions.userEx;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "User cannot be saved!")
public class UserCannotSaveException extends Throwable {
   
	private static final long serialVersionUID = 1L;

	public UserCannotSaveException(String msg) {
        super(msg);
    }
}
