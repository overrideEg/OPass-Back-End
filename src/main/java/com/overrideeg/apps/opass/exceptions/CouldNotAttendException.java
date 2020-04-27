/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.overrideeg.apps.opass.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class CouldNotAttendException extends RuntimeException {

    public CouldNotAttendException() {
        super();
    }

    public CouldNotAttendException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotAttendException(Throwable cause) {
        super(cause);
    }

    protected CouldNotAttendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    public CouldNotAttendException(String message) {
        super(message);
    }
}
