package com.example.filter;

import com.example.http.HttpStatus;
import com.example.http.MockRequest;
import com.example.http.MockResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class SecurityFilterTest {

    @Test
    public void 보안필터_exe경로_블락(){
        // given
        MockRequest request = new MockRequest();
        MockResponse response = new MockResponse();
        request.setPath("http://localhost/hypeboy.exe");

        // when
        SecurityFilter filter = SecurityFilter.getInstance();
        filter.doFilter(request, response);

        // then
        assertEquals(HttpStatus.FORBIDDEN ,response.getHttpStatus());
    }

    @Test
    public void 보안필터_상위경로_블락(){
        // given
        MockRequest request = new MockRequest();
        MockResponse response = new MockResponse();
        request.setPath("http://localhost/../hypeboy");

        // when
        SecurityFilter filter = SecurityFilter.getInstance();
        filter.doFilter(request, response);

        // then
        assertEquals(HttpStatus.FORBIDDEN ,response.getHttpStatus());
    }

    @Test
    public void 보안필터_매핑없는_경로_블락(){
        // given
        MockRequest request = new MockRequest();
        MockResponse response = new MockResponse();
        request.setPath("http://localhost/newjeans/hypeboy");

        // when
        SecurityFilter filter = SecurityFilter.getInstance();
        filter.doFilter(request, response);

        // then
        assertEquals(HttpStatus.NOT_FOUND ,response.getHttpStatus());
    }
}