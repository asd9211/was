package com.example.http;

import com.example.filter.DefaultResourceFilter;
import com.example.filter.FilterChain;
import com.example.handler.RequestMappingHandler;
import com.example.filter.SecurityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class RequestProcessor implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RequestProcessor.class);

    private Socket connection;

    public RequestProcessor(Socket socket) {
        this.connection = socket;
    }

    @Override
    public void run() {
        try {
            HttpConnectionRequest request = RequestParser.parseRequest(connection);
            HttpConnectionResponse response = RequestParser.parseResponse(request);

            requestProcess(request, response);
        } catch (IOException e) {
            log.error("http request parsing error", e);
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                log.error("connection fail!", e);
            }
        }
    }

    private void requestProcess(HttpRequest request, HttpResponse response) {
        if (!isFiltered(request, response)) {
            sendRequest(request, response);
            sendResponse(response);
        }
    }

    private boolean isFiltered(HttpRequest request, HttpResponse response) {
        DefaultResourceFilter defaultResourceFilter = DefaultResourceFilter.getInstance();
        SecurityFilter securityFilter = SecurityFilter.getInstance();

        FilterChain filterChain = new FilterChain(Arrays.asList(defaultResourceFilter, securityFilter));
        filterChain.doFilter(request, response);

        return filterChain.isBlock();
    }

    public void sendRequest(HttpRequest request, HttpResponse response) {
        RequestMappingHandler requestMappingHandler = RequestMappingHandler.getInstance();
        requestMappingHandler.request(request, response);
    }

    public void sendResponse(HttpResponse response) {
        if (!response.isCommited()) {
            response.send();
        }
    }
}