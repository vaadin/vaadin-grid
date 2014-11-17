package com.vaadin.prototype.webcomponentwrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Utils {
    public static <T extends Annotation> String getAnnotationValue(
            Class<?> klazz, Class<T> annotationClass, boolean throwException,
            String annotationArgument, String defaultValue) {
        T annotation = klazz.getAnnotation(annotationClass);
        if (annotation == null) {
            if (throwException) {
                throw new IllegalArgumentException("Class "
                        + klazz.getCanonicalName() + " is not annotated with @"
                        + annotationClass.getName());
            } else {
                return defaultValue;
            }
        } else {
            try {
                Method valueMethod = annotationClass
                        .getMethod(annotationArgument);
                String value = (String) valueMethod.invoke(annotation);
                return value;
            } catch (NoSuchMethodException | SecurityException
                    | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static <T extends Annotation> String getAnnotationValue(
            Class<?> klazz, Class<T> annotationClass, boolean throwException,
            String defaultValue) {
        return getAnnotationValue(klazz, annotationClass, throwException,
                "value", defaultValue);
    }

    public static <T extends Annotation> String getAnnotationValue(
            Class<?> klazz, Class<T> annotationClass) {
        return getAnnotationValue(klazz, annotationClass, true, null);
    }

    public static String readResource(String fileName) {
        ClassLoader cl = findClassLoader();
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                cl.getResourceAsStream(fileName)))) {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public static ClassLoader findClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
