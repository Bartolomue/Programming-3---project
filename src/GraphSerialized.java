import java.io.Serializable;
import java.util.Map;

/**
 * Created by Maksym on 2017-01-22.
 */
public class GraphSerialized implements Serializable {

    public static Map<String, Note> vertices;
    public static Map<String, String> verticesNames;
    public static Map<String, UnorderedPair<Note>> edges;
    public static Map<String, Double> edgesWeights;
    public static Double threshold;
    public static Integer keywordsNumber;

    GraphSerialized(){};

    GraphSerialized(Map<String, Note> ivertices,
                    Map<String, String> iverticesNames,
                    Map<String, UnorderedPair<Note>> iedges,
                    Map<String, Double> iedgesWeights,
                    Double ithreshold,
                    Integer ikeywordsNumber)
    {
        this.vertices = ivertices;
        this.verticesNames = iverticesNames;
        this.edges = iedges;
        this.edgesWeights = iedgesWeights;
        this.threshold = ithreshold;
        this.keywordsNumber = ikeywordsNumber;
    }



}
