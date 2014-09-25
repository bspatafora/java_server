package com.bspatafora.cobspec.handlers;

import com.bspatafora.core.Handler;
import com.bspatafora.core.constants.Header;
import com.bspatafora.core.Request;
import com.bspatafora.core.Response;
import com.bspatafora.core.constants.Status;

public class Redirect implements Handler {
    public Response response(Request request) {
        Response response = new Response();
        response.setStatus(Status.MOVED_PERMANENTLY);
        response.addHeader(Header.LOCATION + "http://localhost:5000/");
        return response;
    }
}
