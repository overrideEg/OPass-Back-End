package com.overrideeg.apps.opass.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DateWrongException extends RuntimeException {


    public DateWrongException() {
        super();
    }

    public DateWrongException(String message) {
        super(message);
    }

    public DateWrongException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateWrongException(Throwable cause) {
        super(cause);
    }

    protected DateWrongException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
