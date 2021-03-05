package webproject.watchshop.exceptions.addressEx;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import webproject.watchshop.exceptions.CustomBaseException;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Product category is not selected!")
public class AddressIsNotExistException extends CustomBaseException {
    public AddressIsNotExistException(String msg) {
        super(msg);
    }
}
