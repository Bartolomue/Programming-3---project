import java.io.IOException;
import java.util.Map;

/**
 * Created by bartek on 1/14/17.
 */
public class DocumentCustom {
    public String content;
    public Map<String, Double> keywords;

    public DocumentCustom(String _content) throws IOException {
        this.content = _content;
        this.keywords = Extractor.getKeywords("SmartStoplist.txt", this.content);
    }
}
