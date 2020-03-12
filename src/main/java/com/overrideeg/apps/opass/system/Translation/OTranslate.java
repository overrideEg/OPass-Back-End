package com.overrideeg.apps.opass.system.Translation;


import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OTranslate {
//    @Inject
//    static languageService languageService;
    private static String alter = "ALTER TABLE";
    private static String varchar = "varchar(max)";

    public static Set<Class<?>> getEntities() {
        Reflections reflections = new Reflections("com.overrideeg.apps.opass.io.entity");
        return reflections.getTypesAnnotatedWith(javax.persistence.Entity.class);
    }
//
//    public static void createTranslatedColumns(Set<Class<?>> classes) {
//        List<language> languages = languageService.findAll(0,100).stream().
//                filter(lang -> !lang.getAlias()
//                        .equalsIgnoreCase("en")).collect(Collectors.toList());
//        classes.forEach(aClass -> {
//            List<Field> fields = findAnnotatedFields(aClass);
//            StringBuilder newLine = new StringBuilder();
//            StringBuilder _AUD = new StringBuilder();
//            newLine.append(alter).append(" ").append(aClass.getSimpleName()).append(" ").append("ADD").append(" ");
//            _AUD.append(alter).append(" ").append(aClass.getSimpleName()).append("_AUD").append(" ").append("ADD").append(" ");
//            AtomicReference<Boolean> have = new AtomicReference<>(false);
//            fields.forEach(field -> {
//                languages.forEach(language -> {
//                    String fieldNameWithLang = field.getName() + "_" + language.getAlias();
//                    newLine.append(fieldNameWithLang).append(" ").append(varchar).append(" ").append("NULL").append(",");
//                    _AUD.append(fieldNameWithLang).append(" ").append(varchar).append(" ").append("NULL").append(",");
//                    have.set(true);
//                });
//            });
//            if (have.get()) {
//                newLine.setLength(newLine.length() - 1);
//                _AUD.setLength(newLine.length() - 1);
//                Connection conn = null;
//                try {
//                    conn = OConnection.dbConn();
//                    conn.createStatement().executeUpdate(newLine.toString());
//                    conn.createStatement().executeUpdate(_AUD.toString());
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        conn.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//        });
//
//    }

//    public static List<Field> findTranslatedFields(Object sourceClass) {
//        return findAnnotatedFields(sourceClass.getClass());
//    }

//    public static List<Field> findNonTranslatedFields(Object sourceClass) {
////        return findNonAnnotatedFields(sourceClass.getClass());
//    }

    public static List<Field> findAnnotatedFields(Class<?> sourceClass,Class<? extends Annotation> annotation) {
        return Arrays.stream(sourceClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(annotation)).collect(Collectors.toList());
    }

//    private static List<Field> findNonAnnotatedFields(Class<?> sourceClass) {
//        return Arrays.stream(sourceClass.getDeclaredFields())
//                .filter(field -> !field.isAnnotationPresent(Translated.class)).collect(Collectors.toList());
//    }
}
