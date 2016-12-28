package download.data.exception;


public class NotEnoughDiskSpaceException extends RuntimeException {

    public NotEnoughDiskSpaceException(String s) {
        super(s);
    }
}
