package com.example.filter;

import com.example.handler.MappingRule;
import com.example.http.HttpRequest;
import com.example.http.HttpResponse;
import com.example.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SecurityFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

    private SecurityFilter() {
        log.info("SecurityHandler loading complete!");
    }

    private static class SingletonHolder {
        static final SecurityFilter INSTANCE = new SecurityFilter();
    }

    public static SecurityFilter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void doFilter(HttpRequest request, HttpResponse response) {
        String requestUrl = request.getPath();

        if (isForbiddenUrl(requestUrl)) {
            response.responseErrorPage(HttpStatus.FORBIDDEN);
            return;
        }

        if (isNotMapped(requestUrl)) {
            response.responseErrorPage(HttpStatus.NOT_FOUND);
        }
    }

    private boolean isForbiddenUrl(String url) {
        List<String> forbiddenUrls = SecurityRule.getForbiddenUrlList();

        for (String forbiddenUrl : forbiddenUrls) {
            if (url.contains(forbiddenUrl)) {
                return true;
            }
        }

        return false;
    }

    private boolean isNotMapped(String url) {
        String mappingClass = MappingRule.getServletMapping(url);
        return mappingClass == null || mappingClass.isEmpty();
    }
}
