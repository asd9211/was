package com.example.handler;

import com.example.servlet.SimpleServlet;
import com.example.util.ClassFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestMapper {

    private static final Logger log = LoggerFactory.getLogger(RequestMapper.class);

    private Map<String, SimpleServlet> servletMap = new HashMap<>();

    private RequestMapper() {
        String packageName = "com.example";

        try {
            List<Class<?>> classes = ClassFinder.findClassesImplementing(packageName, SimpleServlet.class);
            for (Class<?> clazz : classes) {
                this.servletMap.put(clazz.getName(), (SimpleServlet) clazz.getDeclaredConstructor().newInstance());
            }
            log.info("RequestMapper loading complete!");
        } catch (Exception e) {
            log.error("Servlet Class Loading Fail!", e);
            throw new RuntimeException(e);
        }
    }

    private static class SingletonHolder {
        static final RequestMapper INSTANCE = new RequestMapper();
    }

    public static RequestMapper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public SimpleServlet getMappingServlet(String className) {
        return servletMap.get(className);
    }
}
