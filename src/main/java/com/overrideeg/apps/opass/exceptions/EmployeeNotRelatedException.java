package com.overrideeg.apps.opass.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CHECKPOINT)

public class EmployeeNotRelatedException extends RuntimeException {


    public EmployeeNotRelatedException() {
        super();
    }

    public EmployeeNotRelatedException(String message) {
        super(message);
    }

    public EmployeeNotRelatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeNotRelatedException(Throwable cause) {
        super(cause);
    }

    protected EmployeeNotRelatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
