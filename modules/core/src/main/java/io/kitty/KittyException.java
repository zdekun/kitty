package io.kitty;

/**
 * Kitty异常基类.
 */
public class KittyException extends RuntimeException {

    public KittyException() {
        super();
    }

    public KittyException(String message) {
        super(message);
    }

    public KittyException(String message, Throwable cause) {
        super(message, cause);
    }

    public KittyException(Throwable cause) {
        super(cause);
    }

    protected KittyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
