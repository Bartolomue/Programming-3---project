package DataDownload;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by bartek on 11/6/16.
 */
public class Main {

    public static void main(String[] args) throws IOException {


        Document doc = Jsoup.connect("http://en.wikipedia.org/wiki/Boston").get();
        Element contentDiv = doc.select("div[id=content]").first();

        try{
            PrintWriter writer = new PrintWriter("data/sample.txt", "UTF-8");
            writer.print(contentDiv.text());
            writer.close();
        } catch (Exception e) {
            // do something
        }

        System.out.println("DONE");

    }
}
