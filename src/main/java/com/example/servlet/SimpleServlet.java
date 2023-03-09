package com.example.servlet;

import com.example.http.HttpRequest;
import com.example.http.HttpResponse;

public interface SimpleServlet {

    void service(HttpRequest request, HttpResponse response);
}
