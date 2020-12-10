package com.overrideeg.apps.opass.utils;

import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;

import java.lang.reflect.Field;

public class JPAUtils {
    public static <T> void forceLoadLazyCollections(Class<T> tClass, T entity) {
        if (entity == null) {
            return;
        }
        for (Field field : tClass.getDeclaredFields()) {
            LazyCollection annotation = field.getAnnotation(LazyCollection.class);
            if (annotation != null) {
                try {
                    field.setAccessible(true);
                    Hibernate.initialize(field.get(entity));
                } catch (IllegalAccessException e) {
                  e.printStackTrace();
                }
            }
        }
    }
}
