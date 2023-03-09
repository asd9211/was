package com.example.http;

import java.net.Socket;
import java.util.Map;

public interface HttpRequest {
    String getMethod();

    String getPath();

    String getHost();

    String getVersion();

    Socket getSocket();

    Map<String, String> getParameters();

    String getParameter(String key);
}
