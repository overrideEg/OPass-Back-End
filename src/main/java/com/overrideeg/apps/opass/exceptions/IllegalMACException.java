package com.overrideeg.apps.opass.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalMACException extends RuntimeException {
    public IllegalMACException() {
        super();
    }

    public IllegalMACException(String message) {
        super(message);
    }

    public IllegalMACException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalMACException(Throwable cause) {
        super(cause);
    }

    protected IllegalMACException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
