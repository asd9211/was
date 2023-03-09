package com.example.http;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class VirtualHostTest {

    @Test
    public void 가상호스트_정보_불러오기_인덱스페이지() {
        // given
        MockRequest request = new MockRequest();
        request.setHost("localhost");
        VirtualHost virtualHost = new VirtualHost(request);

        // when
        String indexFileName = virtualHost.getIndexFileName();

        //then
        assertTrue(indexFileName.length() > 1);
    }

    @Test
    public void 가상호스트_정보_불러오기_에러페이지() {
        // given
        MockRequest request = new MockRequest();
        request.setHost("localhost");
        VirtualHost virtualHost = new VirtualHost(request);

        // when
        Map<Integer, String> errorPages = virtualHost.getErrorPage();

        //then
        assertNotNull(errorPages.get(403));
    }
}