package com.example.http;

import com.example.server.HttpServer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertTrue;

public class RequestProcessorTest {

    @BeforeClass
    public static void startServer() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(() -> {
            try {
                new HttpServer().start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void 에러_핸들링_403_상위경로() throws IOException {
        // given
        URL url = new URL("http://localhost/../../../test");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // when
        connection.connect();

        // then
        assertTrue(HttpStatus.FORBIDDEN.getCode() == connection.getResponseCode());
    }

    @Test
    public void 에러_핸들링_403_exe() throws IOException {
        // given
        URL url = new URL("http://localhost/test.exe");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // when
        connection.connect();

        // then
        assertTrue(HttpStatus.FORBIDDEN.getCode() == connection.getResponseCode());
    }

    @Test
    public void 에러_핸들링_404() throws IOException {
        // given
        URL url = new URL("http://localhost/test/test/test");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // when
        connection.connect();

        // then
        assertTrue(HttpStatus.NOT_FOUND.getCode() == connection.getResponseCode());
    }

    @Test
    public void 정상호출() throws IOException {
        // given
        URL url = new URL("http://localhost/Hello");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // when
        connection.connect();

        // then
        assertTrue(HttpStatus.OK.getCode() == connection.getResponseCode());
    }

    @Test
    public void 현재시간_출력() throws IOException {
        // given
        URL url = new URL("http://localhost/Hello");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // when
        connection.connect();
        String body = readBody(connection);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = now.format(formatter);

        // then
        assertTrue(body.contains(formattedDateTime));
    }

    private String readBody(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

}