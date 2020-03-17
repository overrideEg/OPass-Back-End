/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.overrideeg.apps.opass.utils;


import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author sergeykargopolov
 */
public class EntityUtils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final int ITERATIONS = 10000;
    private final int KEY_LENGTH = 256;


    /*A UUID (Universal Unique Identifier) is a 128-bit number used to
    uniquely identify some object or entity on the Internet.
    UUID is either guaranteed to be different or is, at least,
    extremely likely to be different from any other UUID generated. */
    public String generateUUID() {
        String returnValue = UUID.randomUUID().toString().replaceAll("-", "");
        return returnValue;
    }

    public int getRandom(int min, int max) {
        Random rand = new Random();
        // nextInt as provided by Random is exclusive of the top value so you need to add 1
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

    public String generateEntityId(int length) {
        return generateRandomString(length);
    }

    public String generateEmailverificationToken(int length) {
        return generateRandomString(length);
    }


    public String getSalt(int length) {
        return generateRandomString(length);
    }

    public String generateSecurePassword(String password, String salt) {
        String returnValue = null;

        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        returnValue = Base64.getEncoder().encodeToString(securePassword);

        return returnValue;
    }

    public byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public byte[] encrypt(String securePassword, String accessTokenMaterial) throws InvalidKeySpecException {
        return hash(securePassword.toCharArray(), accessTokenMaterial.getBytes());
    }

    public Date ConvertDateString(String date) throws ParseException {
        Date convertedDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        return convertedDate;
    }

    ////
    public static String encode(String stringToEncode) {
        return Base64.getEncoder().encodeToString(StringUtils.getBytesUtf8(stringToEncode));
    }

    public static String decode(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }

    public <SRC, DIST> DIST copy(SRC src, DIST dist) {
        DIST returnValue = null;
        try {
            returnValue = (DIST) dist.getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(src, dist);
        returnValue = dist;
        return returnValue;
    }

    public static <T> Collector<T, ?, List<T>> lastN(int n) {
        return Collector.<T, Deque<T>, List<T>>of(ArrayDeque::new, (acc, t) -> {
            if (acc.size() == n)
                acc.pollFirst();
            acc.add(t);
        }, (acc1, acc2) -> {
            while (acc2.size() < n && !acc1.isEmpty()) {
                acc2.addFirst(acc1.pollLast());
            }
            return acc2;
        }, ArrayList::new);
    }

    public static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }

    public int getSumofIndex(Integer number) {
        for (int i = number; i >= 0; i--) {
            if (i == number)
                continue;
            else
                number = number + i;
        }
        return number;
    }

    public boolean isConsecutive(ArrayList<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1) + 1 != list.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static String fieldTypes(String name) {
        String returnValue = null;
        switch (name) {
            case "String":
                returnValue = "String";
                break;
            case "Double":
                returnValue = "Double";
                break;
            case "Float":
                returnValue = "Float";
                break;
            case "double":
                returnValue = "double";
                break;
            case "float":
                returnValue = "float";
                break;
            case "BigDecimal":
                returnValue = "BigDecimal";
                break;
            case "Integer":
                returnValue = "Integer";
                break;
            case "DayOfWeek":
                returnValue = "DayOfWeek";
                break;
            case "int":
                returnValue = "int";
                break;
            case "Date":
                returnValue = "Date";
                break;
            case "long":
                returnValue = "long";
                break;
            case "Long":
                returnValue = "Long";
                break;
            case "Boolean":
                returnValue = "Boolean";
                break;
            case "boolean":
                returnValue = "boolean";
                break;
        }
        return returnValue;
    }

    public static Object runGetter(Field field, Object request) {
        Object value = null;
        try {
            String name = field.getName();
            name = capitalizeFirst(name);
            value = request.getClass().getMethod("get" + name).invoke(request);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;

    }

    public static List<Object> runListGetter(Field field, Object request) {
        List<Object> value = null;
        try {
            String name = field.getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            value = (List<Object>) request.getClass().getMethod("get" + name).invoke(request);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;

    }

    public static void runSetter(Field field, Object destination, Object source) {
        try {
            String name = field.getName();
            name = capitalizeFirst(name);
            Method method = destination.getClass().getMethod("set" + name, field.getType());
            method.setAccessible(true);
            method.invoke(destination, source);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void runSetter(Field field, Object request, List<Object> record) {
        Object value = null;
        try {
            String name = field.getName();
            name = capitalizeFirst(name);
            Method method = request.getClass().getMethod("set" + name, field.getType());
            method.setAccessible(true);
            method.invoke(request, record);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Boolean isList(Field type) {
        return type.getType().getSimpleName().equals("List");
    }

    public static void createFile(String path, String value) {
        String filePath = Paths.get("C:\\pos") + File.separator + path;
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(value);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String capitalizeFirst(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        while (rs.next()) {
            Map<String, Object> row = new HashMap<String, Object>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            rows.add(row);
        }
        return rows;
    }

    /**
     * Find annotated fields list.
     *
     * @param sourceClass the source class
     * @param annotation  the annotation
     * @return the list
     */
    public static List<Field> findAnnotatedFields(Class<?> sourceClass, Class<? extends Annotation> annotation) {
        return Arrays.stream(sourceClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(annotation)).collect(Collectors.toList());
    }
}
