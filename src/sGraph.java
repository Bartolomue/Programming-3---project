import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
/**
 * Created by Maksym on 2017-01-13.
 */
public class sGraph {
    private Graph internalGraph;

    public sGraph()
    {
        internalGraph = new SingleGraph("internalGraphID");

    }

    public void addNode(String key, String sourceId, String destinationId)
    {
        if()
        internalGraph.addNode(key);
        internalGraph.addEdge(sourceId+destinationId, sourceId, destinationId);
    }

}
