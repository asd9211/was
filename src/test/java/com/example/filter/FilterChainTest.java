package com.example.filter;

import com.example.http.MockRequest;
import com.example.http.MockResponse;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.http.HttpRequest;
import com.example.http.HttpResponse;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class FilterChainTest {

    @Test
    public void 필터_블락() {
        // given
        MockRequest request = new MockRequest();
        MockResponse response = new MockResponse();
        TestFilter filter1 = new TestFilter(request, response);
        TestFilter filter2 = new TestFilter(request, response);

        // when
        response.setCommit(true);
        FilterChain filterChain = new FilterChain(Arrays.asList(filter1, filter2));
        filterChain.doFilter(request, response);

        // then
        assertTrue(filterChain.isBlock());
    }

    @Test
    public void 필터_패스() {
        // given
        MockRequest request = new MockRequest();
        MockResponse response = new MockResponse();
        TestFilter filter1 = new TestFilter(request, response);
        TestFilter filter2 = new TestFilter(request, response);

        // when
        response.setCommit(false);
        FilterChain filterChain = new FilterChain(Arrays.asList(filter1, filter2));
        filterChain.doFilter(request, response);

        // then
        assertFalse(filterChain.isBlock());
    }

    private static class TestFilter implements Filter {
        private MockRequest request;
        private MockResponse response;

        public TestFilter(MockRequest request, MockResponse response) {
            this.request = request;
            this.response = response;
        }

        public void doFilter(HttpRequest request, HttpResponse response) {
            MockResponse mockResponse = (MockResponse) response;
        }
    }
}