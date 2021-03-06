package webproject.watchshop.exceptions.blogEx;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import webproject.watchshop.exceptions.CustomBaseException;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Blog category is not selected!")
public class BlogCategoryNotValid extends CustomBaseException {

    public BlogCategoryNotValid(String msg) {
        super(msg);
    }
}