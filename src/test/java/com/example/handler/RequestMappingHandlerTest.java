package com.example.handler;

import com.example.http.HttpStatus;
import com.example.http.MockRequest;
import com.example.http.MockResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestMappingHandlerTest {

    @Test
    public void 매핑핸들링_서블릿_매핑_성공() {
        // given
        MockRequest request = new MockRequest();
        MockResponse response = new MockResponse();
        RequestMappingHandler requestMappingHandler = RequestMappingHandler.getInstance();

        request.setMethod("GET");
        request.setPath("/Hello");

        // when
        requestMappingHandler.request(request, response);

        // then
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getHttpStatus());
    }

    @Test
    public void 매핑핸들링_매핑클래스_없음() {
        // given
        MockRequest request = new MockRequest();
        MockResponse response = new MockResponse();
        RequestMappingHandler requestMappingHandler = RequestMappingHandler.getInstance();

        request.setMethod("GET");
        request.setPath("/yarn");

        // when
        requestMappingHandler.request(request, response);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getHttpStatus());
    }
}