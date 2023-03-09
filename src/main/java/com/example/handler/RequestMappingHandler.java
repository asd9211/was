package com.example.handler;

import com.example.http.HttpRequest;
import com.example.http.HttpResponse;
import com.example.http.HttpStatus;
import com.example.servlet.SimpleServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class RequestMappingHandler {

    private static final Logger log = LoggerFactory.getLogger(RequestMappingHandler.class);

    private RequestMappingHandler() {
        log.info("RequestMappingHandler loading complete!");
    }

    private static class SingletonHolder {
        static final RequestMappingHandler INSTANCE = new RequestMappingHandler();
    }

    public static RequestMappingHandler getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void request(HttpRequest request, HttpResponse response) {
        String mappedServletName = MappingRule.getServletMapping(request.getPath());
        SimpleServlet servlet = getServlet(mappedServletName);
        String method = request.getMethod();

        if (Objects.isNull(servlet)) {
            log.error("this class Not Exists {}", mappedServletName);
            response.responseErrorPage(HttpStatus.INTERNAL_SERVER_ERROR);
            return;
        }

        switch (method) {
            case "GET":
                servlet.service(request, response);
                break;
            case "POST":
                break;
            case "PUT":
                break;
            case "DELETE":
                break;
            default:
                break;
        }
    }

    private SimpleServlet getServlet(String servletName) {
        RequestMapper requestMapper = RequestMapper.getInstance();
        return requestMapper.getMappingServlet(servletName);
    }

}
