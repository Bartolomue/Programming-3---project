import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bartek on 1/15/17.
 */
public class SampleData {

    public static List<Note> docs = new ArrayList<Note>();

    private static final List<String> sampleTopics = Arrays.asList("Logic", "Mathematics", "Pattern", "Warsaw", "Boston", "New_York", "London", "Kraków", "Math");
    private static final String sampleUrl = "https://en.wikipedia.org/wiki/";
    private static final String sampleFolderPath = "data" + java.io.File.separator + "Files";

    public static void createFilesFromWeb() throws IOException {
        for (String topic : sampleTopics) {
            try {
                TextFile.createFile(TextFile.buildPath(sampleFolderPath, topic), TextFile.getContentSite(sampleUrl + topic));
                System.out.println("Text file created.");
            } catch (Exception c) {
                System.out.println("Such topic probably does not exist.");
            }
        }
    }

    public static List<Note> getSampleNotesFromFiles() throws IOException {
        List<Note> notes = new ArrayList<>();
        for (String topic : sampleTopics) {
            notes.add(new Note(Paths.get(TextFile.buildPath(sampleFolderPath, topic))));
        }
        return notes;
    }

}
