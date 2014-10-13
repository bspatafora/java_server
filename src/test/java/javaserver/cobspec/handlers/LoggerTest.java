package javaserver.cobspec.handlers;

import javaserver.cobspec.Resources;
import javaserver.core.Request;
import javaserver.core.constants.Method;
import javaserver.core.constants.Status;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoggerTest {
    @Test
    public void createLog() throws Exception {
        String requestString = "GET /some_route HTTP/1.1\r\n";
        assertFalse("Log resource does not initially contain request string", Resources.logsResource.contains(requestString));

        Request request = new Request();
        request.setMethod(Method.GET);
        request.setRoute("/some_route");
        request.setProtocolVersion(Status.HTTP11);

        new Logger().response(request);
        assertTrue("Log resource contains request string of passed request", Resources.logsResource.contains(requestString));
    }

    @Test
    public void createMultipleLogs() throws Exception {
        String requestOneString = "PUT /foo HTTP/1.1\r\n";
        String requestTwoString = "DELETE /bar HTTP/1.1\r\n";

        Request requestOne = new Request();
        requestOne.setMethod(Method.PUT);
        requestOne.setRoute("/foo");
        requestOne.setProtocolVersion(Status.HTTP11);

        new Logger().response(requestOne);

        Request requestTwo = new Request();
        requestTwo.setMethod(Method.DELETE);
        requestTwo.setRoute("/bar");
        requestTwo.setProtocolVersion(Status.HTTP11);

        new Logger().response(requestTwo);

        assertTrue("Log resource contains request string of first passed request", Resources.logsResource.contains(requestOneString));
        assertTrue("Log resource contains request string of second passed request", Resources.logsResource.contains(requestTwoString));
    }
}