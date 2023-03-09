package com.example.config;

import com.example.http.VirtualHost;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ServerConfig {

    private static final Logger log = LoggerFactory.getLogger(ServerConfig.class);

    private int port;
    private int connectionPoolSize;
    private JSONObject config;

    public ServerConfig() throws IOException {
        try (InputStream stream = VirtualHost.class.getResourceAsStream("/application.json")) {
            String application = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            config = new JSONObject(application);
        }

        this.port = config.getInt("port");
        this.connectionPoolSize = config.getInt("connectionPoolSize");
        log.info("server config loading complete!");
    }

    public int getPort() {
        return port;
    }

    public int getConnectionPoolSize() {
        return connectionPoolSize;
    }

}
