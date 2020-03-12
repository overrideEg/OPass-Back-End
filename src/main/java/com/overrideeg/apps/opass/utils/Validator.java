package com.overrideeg.apps.opass.utils;


import com.overrideeg.apps.opass.exceptions.DateWrongException;
import com.overrideeg.apps.opass.exceptions.MissingRequiredFieldException;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Validator {

    public <T> void checkRequiredField(T requestObject) {

        Field[] fields = requestObject.getClass().getDeclaredFields();
        Long fromDate = null;
        Long toDate = null;
        ArrayList<Object> nonFilledFields = new ArrayList<>();

        for (Field field : fields) {
            List<Annotation> annotations = Arrays.asList(field.getAnnotations());
            boolean req = annotations.stream().anyMatch(annotation -> annotation.annotationType().getName().equals("com.overrideeg.pos.annotations.Req"));
            if (req) {
                String fieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                try {
                    Object invoke = requestObject.getClass().getMethod("get" + fieldName).invoke(requestObject);
                    if (invoke == null) {
                        nonFilledFields.add(fieldName);
                    }

                    if (fieldName.equalsIgnoreCase("fromDate") || fieldName.equalsIgnoreCase("fromTime")) {
                        fromDate = (Long) requestObject.getClass().getMethod("get" + fieldName).invoke(requestObject);
                    }

                    if (fieldName.equalsIgnoreCase("toDate") || fieldName.equalsIgnoreCase("toTime")) {
                        toDate = (Long) requestObject.getClass().getMethod("get" + fieldName).invoke(requestObject);
                    }
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }

        if (nonFilledFields.size() > 0) {
            StringBuilder fieldToError = new StringBuilder();
            for (Object nonFilledField : nonFilledFields) {
                fieldToError.append(nonFilledField).append(" - ");
            }

            fieldToError.setLength(fieldToError.length() - 3);
            throw new MissingRequiredFieldException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage() + fieldToError);
        }  else if (toDate < fromDate) {
            throw new DateWrongException(ErrorMessages.DATE_WRONG_EXCEPTION.getErrorMessage());
        }

    }


}
