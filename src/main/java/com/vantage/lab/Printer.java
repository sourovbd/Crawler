package com.vantage.lab;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

import static com.vantage.lab.Constants.*;

public class Printer {

    public static StringBuilder printInfo(Element link, Document doc) throws IOException {

        StringBuilder text = new StringBuilder(EMPTY_STRING);
        Elements elementsByClass = doc.getElementsByClass(SEARCH_RESULT_ITEM_BODY_TAG);
        for (Element element : elementsByClass) {
            Elements titles = element.getElementsByTag(ANCHOR_TAG);
            Elements authors = element.getElementsByClass(SEARCH_RESULT_AUTHOR_TAG);
            Elements date = element.getElementsByClass(SEARCH_RESULT_DATE_TAG);
            text.append((COCHRANE_LIBRARY_URL + titles.get(0).attr(HREF_TAG))+ PIPE + link.text() + PIPE +
                    titles.get(0).text() + PIPE + ((authors.size()>0) ? (authors.get(0).text()) : "") + PIPE +
                    date.get(0).text() + DOUBLE_NEWLINE);
        }
        return text;
    }

    public static void writeInTextFile(StringBuilder text) throws IOException {
        String filePath = System.getProperty(USER_DIRECTORY) + FILE_NAME;
        FileWriter writer = new FileWriter(filePath);
        writer.write(text.toString());
        writer.close();
    }

}
