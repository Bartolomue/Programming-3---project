/**
 * Created by bartek on 11/6/16.
 */
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.*;

import java.io.IOException;

public class TestMain1 {

    public static void main(String[] args) throws IOException {
        Graph graph = new SingleGraph("Tutorial 1");
        System.out.println("Working!\n");
        int k = 2/3;
        /*
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");

        graph.display();
        */

        sGraph myTestGraph = new sGraph();
        myTestGraph.giveNode("first");
        myTestGraph.giveNode("second");
       // myTestGraph.displayGraph();
        /*
        int[] a = {1,2,3,4,5,6,7,8,9};
        int[] b = {9,8,7,6,5,4,3,2,1};
        myTestGraph.testValues(a,b);
        */

        //myTestGraph.testConnection();
//
//        Note d1 = new Note(Text.getSampleLocal1());
//        Note d2 = new Note(Text.getSampleLocal2());
//        System.out.println("Phase 2 starting...");
//        myTestGraph.testConnection(d1.keywords, d2.keywords);
        //System.out.println(d1.keywords);
        //System.out.println(d2.keywords);
    }

}

//test main1
