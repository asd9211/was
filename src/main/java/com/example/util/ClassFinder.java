package com.example.util;

import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassFinder {

    private ClassFinder() {
    }

    public static List<Class<?>> findClassesImplementing(String packageName, Class<?> type) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> subTypes = reflections.getSubTypesOf((Class<Object>) type);
        return subTypes.stream()
                       .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                       .collect(Collectors.toList());
    }
}
