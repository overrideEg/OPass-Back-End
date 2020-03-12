package com.overrideeg.apps.opass.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InvocationTargetException  extends RuntimeException {
    public InvocationTargetException() {
        super();
    }

    public InvocationTargetException(String message) {
        super(message);
    }

    public InvocationTargetException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvocationTargetException(Throwable cause) {
        super(cause);
    }

    protected InvocationTargetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
