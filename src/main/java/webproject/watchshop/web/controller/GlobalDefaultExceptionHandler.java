package webproject.watchshop.web.controller;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import webproject.watchshop.exceptions.CustomBaseException;
import webproject.watchshop.exceptions.addressEx.AddressIsNotExistException;
import webproject.watchshop.exceptions.userEx.UserCannotSaveException;
import webproject.watchshop.exceptions.userEx.UserRegistrationException;
import webproject.watchshop.util.annotation.PageTitle;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @PageTitle(name = "Error")
    @ExceptionHandler({AddressIsNotExistException.class,
            UsernameNotFoundException.class, UserRegistrationException.class,
            AddressIsNotExistException.class, CustomBaseException.class, UserCannotSaveException.class})
    @GetMapping("/error")
    public ModelAndView handleUserException(HttpServletRequest req, CustomBaseException e) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", e.getMessage());
        return mav;
    }
}
