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
    public Map<String, Double> keywords;
    private static final long serialVersionUID = 1113799434508676095L;

    public Note(String _name, String _content) throws IOException {
        this.id = UUID.randomUUID().toString();
        this.name = _name;
        this.content = _content;
        // coment down
        this.keywords = Extractor.getKeywords("data/Dictionaries/SmartStoplist.txt", this.content);
    }

    public Note(Path path) throws IOException {
        this(TextFile.getFileNameByPath(path), TextFile.getContentFile(path));
    }

}
