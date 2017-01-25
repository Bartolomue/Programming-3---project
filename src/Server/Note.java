package Server;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

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
        this.size = Integer.valueOf(this.content.length());
    }

    public Note(Path path) throws IOException {
        this(TextFile.getFileNameByPath(path), TextFile.getContentFile(path));
    }
}
