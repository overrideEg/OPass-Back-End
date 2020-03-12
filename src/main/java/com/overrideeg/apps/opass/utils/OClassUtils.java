package com.overrideeg.apps.opass.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class OClassUtils {
    public static Class<?> getServiceByName(String name) {
        List<Class<?>> allClasses = find("com.overrideeg.apps.opass.service");
        Map<String, Class<?>> classBySimpleName = new HashMap<>();

        for (Class<?> c : allClasses) {
            classBySimpleName.put(c.getSimpleName(), c);
        }
        return classBySimpleName.get(name + "Service");
    }

    public static Class getRepByName(String name){
        List <Class <?>> allClasses = find("com.overrideeg.apps.opass.io.repositories");
        Map <String, Class<?>> classBySimpleName = new HashMap <>();

        for(Class<?> c : allClasses) {
            classBySimpleName.put(c.getSimpleName(), c);
        }
        return classBySimpleName.get(name + "Repo");
    }
    private static final char PKG_SEPARATOR = '.';

    private static final char DIR_SEPARATOR = '/';

    private static final String CLASS_FILE_SUFFIX = ".class";

    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    public static List<Class<?>> find(String scannedPackage) {
        String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(find(file, scannedPackage));
        }
        return classes;
    }

    private static List<Class<?>> find(File file, String scannedPackage) {
        List <Class<?>> classes = new ArrayList <Class<?>>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }
    public static Method findMethod(Object m, String methodName, Class[] paramsTypes) {
        Method[] metody = m.getClass().getDeclaredMethods();
        List<Method> sameNames = new ArrayList<Method>();
        // filter other names
        for (Method meth : metody) {
            if (meth.getName().equals(methodName)) {
                sameNames.add(meth);
            }
        }
        // lets find best candidate
        if (sameNames.isEmpty()) {
            return null;
        } else {
            // filter other count of parameters
            List<Method> sameCountOfParameters = new ArrayList<Method>();
            for (Method meth : sameNames) {
                if (meth.getParameterTypes().length == paramsTypes.length) {
                    sameCountOfParameters.add(meth);
                }
            }
            if (sameCountOfParameters.isEmpty()) {
                return null;
            } else {
                for (Method meth : sameCountOfParameters) {
                    // first one, which is suitable is the best
                    Class<?>[] params = meth.getParameterTypes();
                    boolean good = true;
                    for (int i = 0; i < params.length && good; i++) {
                        if (params[i].isInterface() && Arrays.asList(paramsTypes[i].getInterfaces()).contains(params[i])) {
                            //if i-th paramater type is Interface and we search method with its implementation
                            good = true;
                            continue;
                        } else {
                            // if we call it with subclass and parameter typ is superclass
                            if (paramsTypes[i].getSuperclass().equals(params[i])) {
                                good = true;
                                continue;
                            }
                        }
                        good = false;
                    }
                    if (good) {
                        return meth;
                    }
                }
            }
        }
        return null;
    }
    public static Field findUnderlying(Class<?> clazz, String fieldName) {
        Class<?> current = clazz;
        do {
            try {
                return current.getDeclaredField(fieldName);
            } catch(Exception e) {}
        } while((current = current.getSuperclass()) != null);
        return null;
    }

}
