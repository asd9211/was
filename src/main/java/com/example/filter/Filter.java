package com.example.filter;

import com.example.http.HttpRequest;
import com.example.http.HttpResponse;

public interface Filter {

    void doFilter(HttpRequest request, HttpResponse response);
}
