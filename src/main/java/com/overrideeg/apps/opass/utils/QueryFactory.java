package com.overrideeg.apps.opass.utils;


import com.overrideeg.apps.opass.service.AbstractService;

import java.util.Arrays;
import java.util.List;

class Queries {

}

public class QueryFactory {
    public static <E, T extends AbstractService> E FindOne(String[] namesArray, Object[] valuesArray, T service, E entity) {
        final List<String> names = Arrays.asList(namesArray);
        final List values = Arrays.asList(valuesArray);
        return (E) service.find(names, values);
    }



}
