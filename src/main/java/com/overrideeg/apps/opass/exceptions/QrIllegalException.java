/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class QrIllegalException extends RuntimeException {

    public QrIllegalException () {
        super();
    }

    public QrIllegalException ( String message, Throwable cause ) {
        super(message, cause);
    }

    public QrIllegalException ( Throwable cause ) {
        super(cause);
    }

    protected QrIllegalException ( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    public QrIllegalException ( String message ) {
        super(message);
    }
}


