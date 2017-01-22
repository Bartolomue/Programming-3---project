import java.io.IOException;

/**
 * Created by bartek on 1/14/17.
 */
public class TestMain2 {
    public static void main(String argv[]) throws IOException {

        Visualization v = null;
        try {
            //SampleData.createFilesFromWeb();

            v = new Visualization(SampleData.getSampleNotesFromFiles(), 0.85, 1);
            v.saveGraph("testLocation");
            System.out.println("Visualization done.");

           // v= new Visualization();
            System.out.println("Visualization 2 start!!!");
            v.loadGraph("testLocation");
            System.out.println("Visualization 2 done!!!");


        } catch (Exception e) {
            System.out.println("Visualization has failed.");
        }

        if (v != null) {
           // v.drawGraph();
        }
    }
}
