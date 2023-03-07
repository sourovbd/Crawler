package com.sv.io.handler;

import com.sv.io.Configuration;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class HttpRequestHandler {
    public static String getSessionId(String url) throws IOException {
        HttpResponse httpresponse;
        String cookie = null;
        CloseableHttpClient httpClient = Configuration.getConfiguredHttpClient();
        HttpGet httpget = new HttpGet(url);
        try {
            httpresponse = httpClient.execute(httpget);
            cookie = Arrays.stream(httpresponse.getAllHeaders())
                    .filter( header -> header.toString().contains("JSESSIONID"))
                    .map(header -> header.toString())
                    .collect(Collectors.joining());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
        return cookie;
    }

    public static String getHtmlByUrl(String url, String sessionId) throws IOException {
        String httpresponse = null;
        CloseableHttpClient httpClient = Configuration.getConfiguredHttpClient();

        String cookie = sessionId.split(":")[1].split(";")[0].trim();

        ResponseHandler<String> responseHandler = new HttpResponseHandler();
        HttpGet request = new HttpGet(url);
        request.setHeader("Cookie", cookie);
        try {
            httpresponse = httpClient.execute(request, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
        return httpresponse;
    }
}
