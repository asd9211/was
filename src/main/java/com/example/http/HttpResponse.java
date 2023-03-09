package com.example.http;

import java.io.IOException;

public interface HttpResponse {
    void setStatus(HttpStatus status);

    void setContentType(String contentType);

    void setHeader(String key, String value);

    HttpStatus getHttpStatus();

    String getErrorPageHtml() throws IOException;

    String getIndexPageHtml() throws IOException;

    boolean isCommited();

    void write(String content);

    void send();

    void responseErrorPage(HttpStatus status);

    void responseIndexPage();
}
