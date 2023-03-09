package com.example.server;


import com.example.config.ServerConfig;
import com.example.http.RequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private static final Logger log = LoggerFactory.getLogger(HttpServer.class);

    private final int connectionPoolSize;
    private final int port;

    public HttpServer() throws IOException {
        ServerConfig serverConfig = new ServerConfig();
        this.port = serverConfig.getPort();
        this.connectionPoolSize = serverConfig.getConnectionPoolSize();
    }

    public void start() throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(connectionPoolSize);

        try (ServerSocket server = new ServerSocket(port)) {
            log.info("Accepting connections on port {}", server.getLocalPort());
            while (true) {
                try {
                    Socket socket = server.accept();
                    Runnable r = new RequestProcessor(socket);
                    pool.submit(r);
                } catch (IOException e) {
                    log.error("Error accepting connection!", e);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            HttpServer webserver = new HttpServer();
            webserver.start();
        } catch (IOException e) {
            log.error("Error accepting connection!", e);
        }
    }
}