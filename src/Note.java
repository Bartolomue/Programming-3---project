import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

/**
 * Created by bartek on 1/14/17.
 */
public class Note implements Serializable {
    public String id;
    public String name;
    public String content;
    public Integer size;
    public Map<String, Double> keywords;
    private static final long serialVersionUID = 1113799434508676095L;

    public Note(String _name, String _content) throws IOException {
        this.id = UUID.randomUUID().toString();
        this.name = _name;
        this.content = _content;
        //this.keywords = Extractor.getKeywords("data/Dictionaries/SmartStoplist.txt", this.content);
        this.size = this.content.length();
    }

    public Note(Path path) throws IOException {
        this(TextFile.getFileNameByPath(path), TextFile.getContentFile(path));
    }

}
