package download.data.exception;


public class NotEnoughMemoryException extends RuntimeException {

    public NotEnoughMemoryException(String s) {
        super(s);
    }
}