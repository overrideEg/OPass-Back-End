/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.overrideeg.apps.opass.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CouldNotUpdateRecordException extends RuntimeException {

    public CouldNotUpdateRecordException() {
        super();
    }

    public CouldNotUpdateRecordException(String message) {
        super(message);
    }

    public CouldNotUpdateRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotUpdateRecordException(Throwable cause) {
        super(cause);
    }

    protected CouldNotUpdateRecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
