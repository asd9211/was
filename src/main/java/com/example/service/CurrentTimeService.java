package com.example.service;

import com.example.http.HttpRequest;
import com.example.http.HttpResponse;
import com.example.http.HttpStatus;
import com.example.servlet.SimpleServlet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTimeService implements SimpleServlet {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        // 헤더 설정
        response.setStatus(HttpStatus.OK);
        response.setContentType("text/html; charset=UTF-8");

        // 바디 설정
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        String body = "Date: " + formattedDateTime + "\r\n";
        response.write(body);
    }
}
