package com.example.server;

import com.example.config.ServerConfig;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class HttpServerTest {

    @Test
    public void 설정정보_불러오기_포트() throws IOException {
        // given
        ServerConfig serverConfig = new ServerConfig();

        //when
        int port = serverConfig.getPort();

        //then
        assertTrue(port > 0);
    }

    @Test
    public void 설정정보_불러오기_스레드풀() throws IOException {
        // given
        ServerConfig serverConfig = new ServerConfig();

        //when
        int connectionPoolSize = serverConfig.getConnectionPoolSize();

        //then
        assertTrue(connectionPoolSize > 0);
    }

}