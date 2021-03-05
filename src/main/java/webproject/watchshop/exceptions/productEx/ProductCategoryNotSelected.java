package webproject.watchshop.exceptions.productEx;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import webproject.watchshop.exceptions.CustomBaseException;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Product category is not selected!")
public class ProductCategoryNotSelected extends CustomBaseException {

    private static final long serialVersionUID = 1L;

    public ProductCategoryNotSelected(String msg) {
        super(msg);
    }
}