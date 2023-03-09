package com.example.handler;

import com.example.servlet.SimpleServlet;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RequestMapperTest{

    @Test
    public void 매핑서블릿_가져오기() {
        // given
        RequestMapper requestMapper = RequestMapper.getInstance();
        String className = "com.example.service.CurrentTimeService";

        // when
        SimpleServlet servlet = requestMapper.getMappingServlet(className);

        // then
        assertNotNull(servlet);
    }

}