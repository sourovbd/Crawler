package com.sv.io.handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static java.util.Objects.nonNull;

public class HttpResponseHandler implements ResponseHandler<String> {

    public String handleResponse(final HttpResponse response) throws IOException {
        int resStatus = response.getStatusLine().getStatusCode();
        if (resStatus == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            if (nonNull(entity)) {
                return EntityUtils.toString(entity);
            }
        }
        return response.toString();
    }
}
