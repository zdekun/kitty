package io.kitty.client;

import io.kitty.KittyException;

public class RestfulException extends KittyException {

    private static final long serialVersionUID = 2719482043431734152L;

    public RestfulException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestfulException(Throwable cause) {
        super(cause);
    }
}
