import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bartek on 11/6/16.
 */
public class SiteDownloader {

    public static void main(String[] args) throws IOException {
        makeSampleData();
    }

    public static List<String> getLinksByLink(String url) {
        List<String> linksClean = new ArrayList<>();

//        try {
//            Document doc = Jsoup.connect(url).get();
//            // get the page title
//            String title = doc.title();
//            System.out.println("title: " + title);
//
//            // get all links in page
//            Elements links = doc.select("a[href]");
//            for (Element link : links) {
//                System.out.println("\nlink: " + link.attr("href"));
//                System.out.println("data: " + link.dataset());
//                // get the value from the href attribute
//                if (link.text().contains("//")) {
//                    linksClean.add(link.text());
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Error occured.");
//        }

        return linksClean;
    }

    public static void createFile(String fileName, String fileContent) {
        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.print(fileContent);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error occured while file creation.");
        }
    }

    public static void makeSampleData() throws IOException {
        List<String> list = Arrays.asList("Warsaw", "Boston", "New_York", "London", "Kraków");
        for (String s : list
                ) {
            createFile("data/" + s.replaceAll("\\W+", ""), getContent("https://en.wikipedia.org/wiki/" + s));
        }
    }

    public static String getContent(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element contentDiv = doc.select("div[id=content]").first();
        return contentDiv.text();
    }
}
