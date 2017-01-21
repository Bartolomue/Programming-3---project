import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bartek on 1/15/17.
 */
public class Data {

    //Test commit



    public List<DocumentCustom> docs = new ArrayList<DocumentCustom>();

    public Data() throws IOException {
        docs.add(new DocumentCustom(SiteDownloader.getContent(SiteDownloader.getSampleUrl1())));
        docs.add(new DocumentCustom(SiteDownloader.getContent(SiteDownloader.getSampleUrl2())));
        docs.add(new DocumentCustom(SiteDownloader.getContent(SiteDownloader.getSampleUrl3())));
        docs.add(new DocumentCustom(SiteDownloader.getContent(SiteDownloader.getSampleUrl4())));
        docs.add(new DocumentCustom(SiteDownloader.getContent(SiteDownloader.getSampleUrl5())));
    }
}
