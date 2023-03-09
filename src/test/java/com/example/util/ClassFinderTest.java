package com.example.util;

import com.example.servlet.SimpleServlet;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ClassFinderTest {

    @Test
    public void 서블릿_구현_클래스_찾기() throws IOException, ClassNotFoundException {
        // given
        String packageName = "com.example";

        // when
        List<Class<?>> classes = ClassFinder.findClassesImplementing(packageName, SimpleServlet.class);

        // then
        assertTrue(SimpleServlet.class.isAssignableFrom(classes.get(0)));
    }

}