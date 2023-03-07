package com.sv.io;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import static com.sv.io.Printer.printInfo;

public class Appender {
    public static StringBuilder getAllReviews(double pageNo, String baseUrl, String sessionId, StringBuilder text, Element link) throws IOException {
        for (int i = 1; i <= pageNo; i++) {
            String pageLink = baseUrl + i;
            Document doc2 = Parser.getDocumentFromLink(pageLink, sessionId);
            text.append(printInfo(link, doc2));
        }
        return text;
    }
}
