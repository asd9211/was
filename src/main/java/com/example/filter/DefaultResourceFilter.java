package com.example.filter;

import com.example.http.HttpRequest;
import com.example.http.HttpResponse;
import com.example.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultResourceFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(DefaultResourceFilter.class);

    private DefaultResourceFilter() {
        log.info("DefaultResourceFilter loading complete!");
    }

    private static class SingletonHolder {
        static final DefaultResourceFilter INSTANCE = new DefaultResourceFilter();
    }

    public static DefaultResourceFilter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void doFilter(HttpRequest request, HttpResponse response) {
        String requestUrl = request.getPath();

        if (isIndexPageRequest(requestUrl)) {
            response.setStatus(HttpStatus.OK);
            response.responseIndexPage();
            return;
        }

        if (isFaviconRequest(requestUrl)) {
            response.setStatus(HttpStatus.NO_CONTENT);
            response.send();
        }
    }

    private boolean isIndexPageRequest(String url){
        return url.equals("/");
    }

    private boolean isFaviconRequest(String url){
        return url.contains("favicon.ico");
    }
}
