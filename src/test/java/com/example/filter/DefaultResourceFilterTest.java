package com.example.filter;

import com.example.http.HttpStatus;
import com.example.http.MockRequest;
import com.example.http.MockResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultResourceFilterTest {

    @Test
    public void 인덱스_경로_요청(){
        // given
        MockRequest request = new MockRequest();
        MockResponse response = new MockResponse();
        request.setPath("/");

        // when
        DefaultResourceFilter filter = DefaultResourceFilter.getInstance();
        filter.doFilter(request, response);

        // then
        assertEquals(HttpStatus.OK ,response.getHttpStatus());
    }

    @Test
    public void favicon_요청(){
        // given
        MockRequest request = new MockRequest();
        MockResponse response = new MockResponse();
        request.setPath("http://localhost/favicon.ico");

        // when
        DefaultResourceFilter filter = DefaultResourceFilter.getInstance();
        filter.doFilter(request, response);

        // then
        assertEquals(HttpStatus.NO_CONTENT ,response.getHttpStatus());
    }
}