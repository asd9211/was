package com.example.http;

public enum HttpStatus {
    // Successful
    OK(200, "Request Success!", "HTTP/1.1 200 OK"),
    NO_CONTENT(204, "No Content", "HTTP/1.1 204 NO CONTENT"),

    // Client Error
    BAD_REQUEST(400, "BAD Request!", "HTTP/1.1 400 BAD REQUEST"),
    FORBIDDEN(403, "Request Forbidden!", "HTTP/1.1 403 FORBIDDEN"),
    NOT_FOUND(404, "Page Not Found!", "HTTP/1.1 404 NOT FOUND"),

    // Server Error
    INTERNAL_SERVER_ERROR(500, "Server Error!", "HTTP/1.1 500 INTERNAL SERVER ERROR");

    private int code;
    private String message;
    private String header;

    HttpStatus(int code, String message, String header) {
        this.code = code;
        this.message = message;
        this.header = header;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getHeader() {return header; }
}
