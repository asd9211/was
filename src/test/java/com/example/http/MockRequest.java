package com.example.http;

import java.net.Socket;
import java.util.Map;

public class MockRequest implements HttpRequest {

    private String host;
    private String method;
    private String path;


    public void setHost(String host) {
        this.host = host;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public Socket getSocket() {
        return null;
    }

    @Override
    public Map<String, String> getParameters() {
        return null;
    }

    @Override
    public String getParameter(String key) {
        return null;
    }
}
