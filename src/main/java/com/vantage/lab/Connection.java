package com.vantage.lab;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;

import static com.vantage.lab.Constants.EMPTY_STRING;
import static com.vantage.lab.Constants.USER_AGENTS;

public class Connection {

    public static String getHtmlByUrl(String url) throws IOException {
        String httpresponse = EMPTY_STRING;

        CloseableHttpClient httpClient = getConfiguredHttpClient();
        ResponseHandler<String> responseHandler = new CustomResponseHandler();
        HttpGet httpget = new HttpGet(url);
        try {
            httpresponse = httpClient.execute(httpget, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
        return httpresponse;
    }

    public static CloseableHttpClient getConfiguredHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD)
                .setCircularRedirectsAllowed(true)
                .build();
        return HttpClients.custom()
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .setDefaultRequestConfig(requestConfig)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .setUserAgent(USER_AGENTS)
                .build();
    }

}
