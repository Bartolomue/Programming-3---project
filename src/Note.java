import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * Created by bartek on 1/14/17.
 */
public class Note {
    public String name;
    public String content;
    public Map<String, Double> keywords;

    public Note(String _name, String _content) throws IOException {
        this.name = _name;
        this.content = _content;
        //this.keywords = Extractor.getKeywords("data/Dictonaries/SmartStoplist.txt", this.content);
    }

    public Note(Path path) throws IOException {
        this(Text.getFileNameByPath(path), Text.getContentFile(path));
    }

}
