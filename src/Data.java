import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bartek on 1/15/17.
 */
public class Data {

    public List<DocumentCustom> docs = new ArrayList<DocumentCustom>();

    public void getDataFromWeb() throws IOException {
        docs.add(new DocumentCustom(SiteDownloader.getContent(SiteDownloader.getSampleUrl1())));
        docs.add(new DocumentCustom(SiteDownloader.getContent(SiteDownloader.getSampleUrl2())));
        docs.add(new DocumentCustom(SiteDownloader.getContent(SiteDownloader.getSampleUrl3())));
        docs.add(new DocumentCustom(SiteDownloader.getContent(SiteDownloader.getSampleUrl4())));
        docs.add(new DocumentCustom(SiteDownloader.getContent(SiteDownloader.getSampleUrl5())));
    }

    public void getDataLocalFiles() throws IOException {
        docs.add(new DocumentCustom(getStringFromFile("data/Boston")));
        docs.add(new DocumentCustom(getStringFromFile("data/New_York")));
    }

    public String getStringFromFile(String pathToFile) throws IOException {
        return new String(Files.readAllBytes(Paths.get(pathToFile)));
    }
}
