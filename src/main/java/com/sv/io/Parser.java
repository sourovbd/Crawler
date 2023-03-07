package com.sv.io;

import org.apache.http.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static com.sv.io.Appender.getAllReviews;
import static com.sv.io.Constants.*;
import static com.sv.io.handler.HttpRequestHandler.getHtmlByUrl;
import static com.sv.io.Printer.writeInTextFile;

public class Parser {
    public static void parserHtml(String content, String sessionId, String topicName) throws ParseException, IOException {
        Document doc = Jsoup.parse(content);
        Elements links = doc.select(ANCHOR_TAG);
        StringBuilder text = new StringBuilder(EMPTY_STRING);

        for (Element link : links) {
            if (link.text().equalsIgnoreCase(topicName)) {
                String url = link.attr(HREF_TAG);
                Document doc1 = getDocumentFromLink(url, sessionId);
                double pageNo = getPageNo(doc1);
                String baseUrl = getBaseUrl(doc1);
                text = getAllReviews(pageNo, baseUrl, sessionId, text, link);
            }
        }
        writeInTextFile(text);
    }

    public static Document getDocumentFromLink(String url, String sessionId) throws IOException {
        String attrUrlContent = getHtmlByUrl(url, sessionId);

        return Jsoup.parse(attrUrlContent);
    }

    private static double getPageNo(Document doc) {
        Elements resultNumber = doc.select(RESULT_NUMBER);
        double noOfReviews = Integer.parseInt(resultNumber.text());
        double topicsPerPage = TOPIC_PER_PAGE;
        return Math.ceil(noOfReviews / topicsPerPage);
    }

    private static String getBaseUrl(Document doc) {
        Elements paginationNextPageLinks = doc.select(PAGINATION_NEXT_LINKS_TAG);
        String nextPageLink = paginationNextPageLinks.get(0).attr(HREF_TAG);
        return nextPageLink.substring(0, nextPageLink.length() - 1);
    }
}
