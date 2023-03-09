package com.example.handler;

import java.util.HashMap;
import java.util.Map;

public class MappingRule {

    private static Map<String, String> servletMapping = new HashMap<>();

    static {
        servletMapping.put("/Hello", "com.example.service.CurrentTimeService");
        servletMapping.put("/yarn", "com.example.service.CurrentTimeService2");

    }

    private MappingRule() {
    }

    public static String getServletMapping(String key) {
        return servletMapping.get(key);
    }

}
