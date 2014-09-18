package com.bspatafora.java_server_tests;

import com.bspatafora.java_server.Request;
import com.bspatafora.java_server.RequestFactory;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class RequestTest {
    @Test
    public void requestString() throws Exception {
        String requestString = "POST /form HTTP/1.1\r\nContent-Length: 4\r\n\r\nTest\r\n";
        InputStream inputStream = new ByteArrayInputStream(requestString.getBytes(StandardCharsets.UTF_8));
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        Request request = new RequestFactory(in).build();
        assertEquals("Generated request string is equal to the original request string", requestString, request.requestString());
    }
}