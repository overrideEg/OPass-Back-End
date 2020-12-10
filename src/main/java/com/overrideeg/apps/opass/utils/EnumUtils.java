package com.overrideeg.apps.opass.utils;

import com.overrideeg.apps.opass.annotations.Enums;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class EnumUtils {

    static void Init() {

        List<Class<?>> classes = grabEnums();

        insertEnums(classes);

    }

    private static List <Class <?>> grabEnums() {
        Reflections reflections = new Reflections("com.overrideeg.enums");
        return reflections.getTypesAnnotatedWith(Enums.class).stream().sorted(Comparator.comparing(aClass -> aClass.getAnnotation(Enums.class).priority())).collect(Collectors.toList());
    }



    private static void insertEnums(List<Class<?>> classes) {

        classes.forEach(classX -> {

            List<Field> fields = Arrays.asList(classX.getDeclaredFields());

            fields.forEach(field -> insertField(classX, field));

        });

    }

    private static void insertField(Class<?> classX, Field field) {

        try {

            String fieldType = field.getType().getCanonicalName();

            fieldType = fieldType.replace("entity", "dao.impl");
            Class<?> typeDAOImpl = Class.forName(fieldType + "DAOImpl");

            Constructor constructor = typeDAOImpl.getDeclaredConstructor();
            Object instance = constructor.newInstance();


            Method callMethod = instance.getClass().getMethod("saveOrUpdate", Object.class);
            callMethod.setAccessible(true);
            field.get(classX);

            callMethod.invoke(instance, field.get(classX));


        } catch (Exception e) {
            new Log(EnumUtils.class, e, "while inserting enum : " + classX.getName() + " : " + field.getName());
        }

    }


}
