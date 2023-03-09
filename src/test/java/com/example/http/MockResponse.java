package com.example.http;

import java.io.IOException;
import java.io.PrintWriter;

public class MockResponse implements HttpResponse {

    private boolean commit = false;

    private HttpStatus status;

    @Override
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public void setContentType(String contentType) {

    }

    @Override
    public void setHeader(String key, String value) {

    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.status;
    }

    @Override
    public String getErrorPageHtml() throws IOException {
        return null;
    }

    @Override
    public String getIndexPageHtml() throws IOException {
        return null;
    }

    @Override
    public boolean isCommited() {
        return commit;
    }

    @Override
    public void write(String content) {

    }

    @Override
    public void send() {

    }

    @Override
    public void responseErrorPage(HttpStatus status) {
        this.status = status;
    }

    @Override
    public void responseIndexPage() {

    }

    public boolean setCommit(boolean commit) {
        return this.commit = commit;
    }
}