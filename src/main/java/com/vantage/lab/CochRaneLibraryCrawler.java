package com.vantage.lab;

import org.apache.http.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static com.vantage.lab.Connection.getHtmlByUrl;
import static com.vantage.lab.Constants.*;
import static com.vantage.lab.Printer.printInfo;
import static com.vantage.lab.Printer.writeInTextFile;

public class CochRaneLibraryCrawler {


    public static void main(String[] args) throws IOException {
        String htmlContent = getHtmlByUrl(TOPIC_URL);
        parserHtml(htmlContent);
    }

    public static void parserHtml(String content) throws ParseException, IOException {
        Document doc = Jsoup.parse(content);
        Elements links = doc.select(ANCHOR_TAG);
        StringBuilder text = new StringBuilder(EMPTY_STRING);
        for (Element link : links) {
            if (link.text().equalsIgnoreCase(ALLERGY_AND_INTOLERANCE)) {

                Document doc1 = getDocumentFromLink(link);
                System.out.println("link: "+link);

                text.append(printInfo(link, doc1));

                Elements paginationPageLinks = doc1.select(PAGINATON_LINK_TAG);

                for (Element paginationPageLink : paginationPageLinks) {
                    Document doc2 = getDocumentFromLink(paginationPageLink);
                    System.out.println("paginationPageLink: "+paginationPageLink);
                    text.append(printInfo(link, doc2));
                }
            }
        }
        writeInTextFile(text);
    }


    public static Document getDocumentFromLink(Element link) throws IOException {
        String attrUrl = link.attr(HREF_TAG);
        String attrUrlContent = getHtmlByUrl(attrUrl);

        return Jsoup.parse(attrUrlContent);
    }


}
