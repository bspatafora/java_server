package javaserver.core;

import javaserver.cobspec.RouterFactory;
import javaserver.core.constants.Header;
import javaserver.core.constants.Status;
import javaserver.core.helpers.Stream;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.Assert.*;

public class WorkerTest {
    private static final int port = 9000;
    private static final String localHost = "localhost";
    private static final String getRoot = "GET / HTTP/1.1\r\n";
    private static final String postForm = "POST /form HTTP/1.1\r\nContent-Length: 10\r\n\r\ndata=cosby\r\n";
    private static final String putForm = "PUT /form HTTP/1.1\r\nContent-Length: 15\r\n\r\ndata=heathcliff\r\n";
    private static final String getForm = "GET /form HTTP/1.1\r\n";
    private static final String deleteForm = "DELETE /form HTTP/1.1\r\n";
    private static final String getUnregistered = "GET /unregistered HTTP/1.1\r\n";
    private static final String getRedirect = "GET /redirect HTTP/1.1\r\n";

    @BeforeClass
    public static void startServers() {
        new Thread(new Server(port, new RouterFactory())).start();
    }

    @Test
    public void getRootStatus() throws Exception {
        Socket getRootSocket = new Socket(localHost, port);
        PrintWriter toServer = new PrintWriter(getRootSocket.getOutputStream(), true);
        toServer.println(getRoot);

        String getRootResponse = Stream.toString(getRootSocket.getInputStream());
        assertTrue("Response to GET / is '200 OK'", getRootResponse.contains(Status.OK));
    }

    @Test
    public void getRootBody() throws Exception {
        String link = "<a href=\"/text-file.txt\">text-file.txt</a></br>";

        Socket getRootSocket = new Socket(localHost, port);
        PrintWriter toServer = new PrintWriter(getRootSocket.getOutputStream(), true);
        toServer.println(getRoot);

        String getRootResponse = Stream.toString(getRootSocket.getInputStream());
        assertTrue("Response to GET / has body with the root links", getRootResponse.contains(link));
    }

    @Test
    public void postFormStatus() throws Exception {
        Socket postFormSocket = new Socket(localHost, port);
        PrintWriter toServer = new PrintWriter(postFormSocket.getOutputStream(), true);
        toServer.println(postForm);

        String postFormResponse = Stream.toString(postFormSocket.getInputStream());
        assertTrue("Response to POST /form is '200 OK'", postFormResponse.contains(Status.OK));
    }

    @Test
    public void postGet() throws Exception {
        Socket postFormSocket = new Socket(localHost, port);
        PrintWriter postToServer = new PrintWriter(postFormSocket.getOutputStream(), true);
        postToServer.println(postForm);

        Socket getFormSocket = new Socket(localHost, port);
        PrintWriter getToServer = new PrintWriter(getFormSocket.getOutputStream(), true);
        getToServer.println(getForm);

        String getFormResponse = Stream.toString(getFormSocket.getInputStream());
        assertTrue("Response to GET /form contains previously POSTed data, 'data=cosby'", getFormResponse.contains("data=cosby"));
    }

    @Test
    public void putGetDeleteGet() throws Exception {
        Socket putFormSocket = new Socket(localHost, port);
        PrintWriter putToServer = new PrintWriter(putFormSocket.getOutputStream(), true);
        putToServer.println(putForm);

        String putFormResponse = Stream.toString(putFormSocket.getInputStream());
        assertTrue("Response to PUT /form is '200 OK'", putFormResponse.contains(Status.OK));

        Socket getFormSocket1 = new Socket(localHost, port);
        PrintWriter getToServer1 = new PrintWriter(getFormSocket1.getOutputStream(), true);
        getToServer1.println(getForm);

        String getFormResponse1 = Stream.toString(getFormSocket1.getInputStream());
        assertTrue("Response to GET /form contains previously PUT data, 'data=heathcliff'", getFormResponse1.contains("data=heathcliff"));

        Socket deleteFormSocket = new Socket(localHost, port);
        PrintWriter deleteToServer = new PrintWriter(deleteFormSocket.getOutputStream(), true);
        deleteToServer.println(deleteForm);

        Socket getFormSocket2 = new Socket(localHost, port);
        PrintWriter getToServer2 = new PrintWriter(getFormSocket2.getOutputStream(), true);
        getToServer2.println(getForm);

        String getFormResponse2 = Stream.toString(getFormSocket2.getInputStream());
        assertFalse("Response to GET /form does not contain DELETEd PUT data", getFormResponse2.contains("data=heathcliff"));
    }

    @Test
    public void getUnregisteredStatus() throws Exception {
        Socket getUnregisteredSocket = new Socket(localHost, port);
        PrintWriter toServer = new PrintWriter(getUnregisteredSocket.getOutputStream(), true);
        toServer.println(getUnregistered);

        String getUnregisteredResponse = Stream.toString(getUnregisteredSocket.getInputStream());
        assertTrue("Response to GET /unregistered is '404 Not Found'", getUnregisteredResponse.contains(Status.NOT_FOUND));
    }

    @Test
    public void getUnregisteredBody() throws Exception {
        Socket getUnregisteredSocket = new Socket(localHost, port);
        PrintWriter toServer = new PrintWriter(getUnregisteredSocket.getOutputStream(), true);
        toServer.println(getUnregistered);

        String getUnregisteredResponse = Stream.toString(getUnregisteredSocket.getInputStream());
        assertTrue("Response to GET /unregistered has body 'Not found.'", getUnregisteredResponse.contains(Status.NOT_FOUND));
    }

    @Test
    public void getRedirectStatus() throws Exception {
        Socket getRedirectSocket = new Socket(localHost, port);
        PrintWriter toServer = new PrintWriter(getRedirectSocket.getOutputStream(), true);
        toServer.println(getRedirect);

        String getRedirectResponse = Stream.toString(getRedirectSocket.getInputStream());
        assertTrue("Response to GET /redirect has status '301 Moved Permanently'", getRedirectResponse.contains(Status.MOVED_PERMANENTLY));
    }

    @Test
    public void getRedirectHeader() throws Exception {
        Socket getRedirectSocket = new Socket(localHost, port);
        PrintWriter toServer = new PrintWriter(getRedirectSocket.getOutputStream(), true);
        toServer.println(getRedirect);

        String getRedirectResponse = Stream.toString(getRedirectSocket.getInputStream());
        assertTrue("Response to GET /redirect has location header", getRedirectResponse.contains(Header.LOCATION));
    }
}
