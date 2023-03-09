package com.example.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpConnectionResponse implements HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpConnectionResponse.class);

    private final Map<String, String> headers = new HashMap<>();
    private StringBuilder body = new StringBuilder();
    private HttpStatus httpStatus;
    private OutputStream out;
    private PrintWriter writer;
    private VirtualHost virtualHost;
    private boolean commit = false;


    public HttpConnectionResponse(HttpConnectionRequest request) throws IOException {
        Socket socket = request.getSocket();
        this.out = new BufferedOutputStream(socket.getOutputStream());
        this.writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        this.virtualHost = new VirtualHost(request);
        log.info("ResponseParsing Success");
    }

    @Override
    public void setStatus(HttpStatus status) {
        writer.write(status.getHeader() + "\r\n");
        this.httpStatus = status;
    }

    @Override
    public void setContentType(String contentType) {
        writer.write("Content-Type: " + contentType + "\r\n");
        this.headers.put("Content-Type", contentType);
    }

    @Override
    public void setHeader(String key, String value) {
        writer.write(key + ": " + value + "\r\n");
        this.headers.put(key, value);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getErrorPageHtml() throws IOException {
        return this.virtualHost.getErrorPageHtml(httpStatus);
    }

    @Override
    public String getIndexPageHtml() throws IOException {
        return this.virtualHost.getIndexPageHtml();
    }

    @Override
    public void write(String content) {
        body.append(content);
    }

    @Override
    public void send() {
        if (!commit) {
            try {
                String content = body.toString();
                int len = content.getBytes(StandardCharsets.UTF_8).length;
                writer.write("Content-Length: " + len + "\r\n");
                writer.write("\r\n");
                writer.write(content);
                writer.flush();
                writer.close();
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.commit = true;
        } else {
            log.info("already commited!");
        }
    }

    @Override
    public boolean isCommited() {
        return commit;
    }

    @Override
    public void responseErrorPage(HttpStatus status) {
        try {
            setStatus(status);
            String errPage = getErrorPageHtml();
            setContentType("text/html;charset=UTF-8");
            write(errPage);
            send();
        } catch (IOException e) {
            log.error("response error page create fail!", e);
        }
    }

    @Override
    public void responseIndexPage() {
        try {
            String indexPage = getIndexPageHtml();
            setContentType("text/html;charset=UTF-8");
            write(indexPage);
            send();
        } catch (IOException e) {
            log.error("response index page create fail!", e);
        }
    }

    @Override
    public String toString() {
        return "HttpConnectionResponse{" +
                "headers=" + headers +
                ", out=" + out +
                ", writer=" + writer +
                ", httpStatus=" + httpStatus +
                ", virtualHost=" + virtualHost +
                ", commit=" + commit +
                '}';
    }
}