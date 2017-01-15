import java.io.IOException;

/**
 * Created by bartek on 1/14/17.
 */
public class TestMain2 {
    public static void main(String argv[]) throws IOException {
        Document t = new Document(SiteDownloader.getContent("http://en.wikipedia.org/wiki/Boston"));

        for (String s: t.keywords.keySet()
             ) {
            System.out.println("Key: " + s + " value: " + t.keywords.get(s));
        }
    }
}
