import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bartek on 1/15/17.
 */
public class Data {

    //Test commit

    

    public List<Document> docs = new ArrayList<Document>();

    public Data() throws IOException {
        docs.add(new Document(SiteDownloader.getContent(SiteDownloader.getSampleUrl1())));
        docs.add(new Document(SiteDownloader.getContent(SiteDownloader.getSampleUrl2())));
        docs.add(new Document(SiteDownloader.getContent(SiteDownloader.getSampleUrl3())));
        docs.add(new Document(SiteDownloader.getContent(SiteDownloader.getSampleUrl4())));
        docs.add(new Document(SiteDownloader.getContent(SiteDownloader.getSampleUrl5())));
    }
}
