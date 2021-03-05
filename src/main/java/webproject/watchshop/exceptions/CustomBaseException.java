package webproject.watchshop.exceptions;

public abstract class CustomBaseException extends RuntimeException{
    public CustomBaseException(String message) {
        super(message);
    }
}