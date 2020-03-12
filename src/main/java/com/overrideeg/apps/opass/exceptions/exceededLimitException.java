package com.overrideeg.apps.opass.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class exceededLimitException extends RuntimeException {
    public exceededLimitException() {
        super();
    }

    public exceededLimitException(String message) {
        super(message);
    }

    public exceededLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public exceededLimitException(Throwable cause) {
        super(cause);
    }

    protected exceededLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
