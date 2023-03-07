package com.sv.io;

import com.sv.io.handler.HttpRequestHandler;

import java.io.IOException;

import static com.sv.io.ConfigFileLoader.getTopicName;

public class Main {
    public static void main(String[] args) throws IOException {
        String sessionId = HttpRequestHandler.getSessionId(Constants.TOPIC_URL);
        String htmlContent = HttpRequestHandler.getHtmlByUrl(Constants.TOPIC_URL, sessionId);
        String topicName = getTopicName();
        Parser.parserHtml(htmlContent, sessionId, topicName);
    }
}
