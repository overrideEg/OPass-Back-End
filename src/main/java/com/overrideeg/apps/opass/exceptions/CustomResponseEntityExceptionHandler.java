package com.overrideeg.apps.opass.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleConstraint(DataIntegrityViolationException exception, WebRequest request ) {


        ConstraintViolationException ex = (ConstraintViolationException) exception.getCause();
        java.sql.SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) ex.getCause();
        return new ResponseEntity(new exceptionResponse(new Date(),ex.getErrorCode(),sqlEx.getLocalizedMessage(),sqlEx.getMessage().substring(0,sqlEx.getMessage().indexOf("for key")-1 )), HttpStatus.CONFLICT);
    }


}