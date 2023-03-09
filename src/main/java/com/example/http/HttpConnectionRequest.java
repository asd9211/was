package com.example.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpConnectionRequest implements HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpConnectionRequest.class);

    private String method;
    private String version;
    private String path;
    private Map<String, String> parameters = new HashMap<>();
    private String host;
    private Socket socket;

    public HttpConnectionRequest(Socket socket) throws IOException {
        this.socket = socket;
        InputStream in = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String[] requestLine = reader.readLine().split(" ");

        parseRequestLine(requestLine);
        parseHeaders(reader);
        log.info("RequestParsing Success");
    }

    private void parseRequestLine(String[] requestLine) {
        this.method = requestLine[0];
        this.path = requestLine[1];
        this.version = requestLine.length > 2 ? requestLine[2] : "";
        if (path.contains("?")) {
            String param = path.substring(path.indexOf("?") + 1);
            path = path.substring(0, path.indexOf("?"));
            parseQueryString(param);
        }
    }

    private void parseHeaders(BufferedReader reader) throws IOException {
        String readLine;
        while ((readLine = reader.readLine()) != null) {
            if (readLine.startsWith("Host:")) {
                this.host = readLine.substring(readLine.indexOf(":") + 1).trim();
                break;
            }
        }
    }

    private void parseQueryString(String queryString) {
        String[] pairs = queryString.split("&");

        for (String pair : pairs) {
            String[] keyAndValue = pair.split("=");
            if (keyAndValue.length == 2) {
                String key = keyAndValue[0];
                String value = keyAndValue[1];
                parameters.put(key, value);
            }
        }
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public String getParameter(String key) {
        return parameters.get(key);
    }

    @Override
    public String toString() {
        return "HttpConnectionRequest{" +
                "method='" + method + '\'' +
                ", version='" + version + '\'' +
                ", path='" + path + '\'' +
                ", parameters=" + parameters +
                ", host='" + host + '\'' +
                ", socket=" + socket +
                '}';
    }
}
