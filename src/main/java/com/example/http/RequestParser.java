package com.example.http;

import java.io.IOException;
import java.net.Socket;

public class RequestParser {

    private RequestParser(){}

    public static HttpConnectionRequest parseRequest(Socket socket) throws IOException {
        return new HttpConnectionRequest(socket);
    }

    public static HttpConnectionResponse parseResponse(HttpConnectionRequest request) throws IOException {
        return new HttpConnectionResponse(request);
    }
}
