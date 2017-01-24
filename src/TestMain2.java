import java.io.IOException;

/**
 * Created by bartek on 1/14/17.
 */
public class TestMain2 {
    public static void main(String argv[]) throws IOException, InterruptedException {

        Visualization v = null;
        v= new Visualization();
        try {
            //SampleData.createFilesFromWeb(); - patrzcie zakomentowane a działa
            Integer option=2; // // 1=load, 2=create and save, 3=regular
            // Load from user
            String IP = "";
            int portNumber = 6066;
            String fileName = ""; // loading
            //
            if(option == 1)
            {
                // testLocation has been created without _keywordsNum
                // testFinalLoc is done on server with terms adjusted!

                fileName = "TestFile99";
                v.loadGraph(fileName);
                System.out.println("Visualization load start!!!");
                //v.loadGraph("testLocation");
                //System.out.println("Visualization load done!!!");
            }
            else if(option == 2)
            {
                v = new Visualization(SampleData.getSampleNotesFromFiles(), 0.85, 4);

                v.saveGraph("TestFile99");
                System.out.println("Visualization done.");
            }
            //v = new Visualization(SampleData.getSampleNotesFromFiles(), 0.85, 4);
//            v.saveGraph("testLocation");
//            System.out.println("Visualization done.");
//
//            v= new Visualization();
//            System.out.println("Visualization 2 start!!!");
//            v.loadGraph("testLocation");
//            System.out.println("Visualization 2 done!!!");


        } catch (Exception e) {
            System.out.println("Visualization has failed.");
        }

        if (v != null) {
            v.drawGraph();

        }
    }
}
