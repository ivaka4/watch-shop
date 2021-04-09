package webproject.watchshop.exceptions.blogEx;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import webproject.watchshop.exceptions.CustomBaseException;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Blog can`t be added")
public class BlogAddException extends CustomBaseException {

    public BlogAddException(String msg) {
        super(msg);
    }
}