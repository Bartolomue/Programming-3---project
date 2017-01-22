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
        docs.add(new Note(Text.getSampleLocal1(), "A"));
        docs.add(new Note(Text.getSampleLocal2(), "B"));
        docs.add(new Note(Text.getSampleLocal3(), "C"));
        docs.add(new Note(Text.getSampleLocal4(), "D"));
        docs.add(new Note(Text.getSampleLocal5(), "E"));
    }


}
