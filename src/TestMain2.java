import java.io.IOException;

/**
 * Created by bartek on 1/14/17.
 */
public class TestMain2 {
    public static void main(String argv[]) throws IOException {

        Visualization v = null;
        try {
            SampleData.createFilesFromWeb();
            v = new Visualization(SampleData.getSampleNotesFromFiles(), 0.86);
        } catch (Exception e) {
            System.out.println("Visualization has failed.");
        }

        if (v != null) {
            v.drawGraph();
        }
    }
}
