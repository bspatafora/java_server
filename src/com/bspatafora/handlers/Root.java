package com.bspatafora.handlers;

import com.bspatafora.constants.Headers;
import com.bspatafora.constants.StatusLine;
import com.bspatafora.java_server.*;

public class Root implements Handler {
    public Response response(Request request) {
        Response response = new Response();
        response.setStatus(StatusLine.OK);
        response.setProtocolVersion(StatusLine.HTTP11);
        response.addHeader(Headers.CONTENT_TYPE + Headers.TEXT_HTML);
        response.setBody(body());
        return response;
    }

    private String body() {
        return "<a href=\"/file1\">file1</a></br>" +
               "<a href=\"/file2\">file2</a></br>" +
               "<a href=\"/image.gif\">image.gif</a></br>" +
               "<a href=\"/image.jpeg\">image.jpeg</a></br>" +
               "<a href=\"/image.png\">image.png</a></br>" +
               "<a href=\"/text-file.txt\">text-file.txt</a>";
    }
}