import java.io.IOException;

/**
 * Created by bartek on 1/14/17.
 */
public class TestMain2 {
    public static void main(String argv[]) throws IOException {
        Data d = new Data();

        for (DocumentCustom doc : d.docs){
            System.out.println(doc.toString());
        }
//        DocumentCustom t = new DocumentCustom(SiteDownloader.getContent("http://en.wikipedia.org/wiki/Boston"));
//
//        for (String s: t.keywords.keySet()
//             ) {
//            System.out.println("Key: " + s + " value: " + t.keywords.get(s));
//        }
    }
}
