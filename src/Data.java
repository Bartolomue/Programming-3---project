import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bartek on 1/15/17.
 */
public class Data {

    public List<Note> docs = new ArrayList<Note>();

    public void getDataFromWeb() throws IOException {
        docs.add(new Note(Text.getSampleWeb1(), "A"));
    }

    public void getDataLocalFiles() throws IOException {
        docs.add(new Note(Text.getSamplePath1()));
        docs.add(new Note(Text.getSamplePath2()));
        docs.add(new Note(Text.getSamplePath3()));
        docs.add(new Note(Text.getSamplePath4()));
        docs.add(new Note(Text.getSamplePath5()));

    }


}
