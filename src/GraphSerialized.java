import java.io.Serializable;
import java.util.Map;

/**
 * Created by Maksym on 2017-01-22.
 */
public class GraphSerialized implements Serializable {

    public Map<String, Note> vertices;
    public Map<String, String> verticesNames;
    public Map<String, UnorderedPair<Note>> edges;
    public Map<String, Double> edgesWeights;
    public Double threshold;
    public Integer keywordsNumber;

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
