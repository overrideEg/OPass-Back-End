/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.overrideeg.apps.opass.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CouldNotDeleteRecordException extends RuntimeException {

    public CouldNotDeleteRecordException() {
        super();
    }

    public CouldNotDeleteRecordException(String message) {
        super(message);
    }

    public CouldNotDeleteRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouldNotDeleteRecordException(Throwable cause) {
        super(cause);
    }

    protected CouldNotDeleteRecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
