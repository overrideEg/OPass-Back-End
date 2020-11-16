/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.overrideeg.apps.opass.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CHECKPOINT)
public class MatchingIdsException extends RuntimeException {

    public MatchingIdsException() {
        super();
    }

    public MatchingIdsException(String message) {
        super(message);
    }

    public MatchingIdsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatchingIdsException(Throwable cause) {
        super(cause);
    }

    protected MatchingIdsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
