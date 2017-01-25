import java.io.Serializable;
import java.util.Map;

/**
 * Created by Maksym on 2017-01-22.
 */

/**
 * GraphSerialized - provides IO options for Visualisation objects.
 * Allows storing calculated data (without Graph object) for further usage.
 *
 */
public class GraphSerialized implements Serializable {

    public Map<String, Note> vertices;
    public Map<String, String> verticesNames;
    public Map<String, UnorderedPair<Note>> edges;
    public Map<String, Double> edgesWeights;
    public Double threshold;
    public Integer keywordsNumber;
    public static Integer maxEdgeWidth = 4;
    public static Double maxWeight;
    public  static Double minWeight;

    public  static Integer maxNodeSize = 24;
    public  static Integer maxLength;
    public  static Integer minLength;
    public  boolean loop = true;

    GraphSerialized(){};

    GraphSerialized(Map<String, Note> ivertices,
                    Map<String, String> iverticesNames,
                    Map<String, UnorderedPair<Note>> iedges,
                    Map<String, Double> iedgesWeights,
                    Double ithreshold,
                    Integer ikeywordsNumber,
                    Integer imaxedgeWidth,
                    Double imaxWeight,
                    Double iminWeight,
                    Integer imaxNodeSize,
                    Integer imaxLength,
                    Integer iminLength,
                    boolean iloop)
    {
        this.vertices = ivertices;
        this.verticesNames = iverticesNames;
        this.edges = iedges;
        this.edgesWeights = iedgesWeights;
        this.threshold = ithreshold;
        this.keywordsNumber = ikeywordsNumber;
        this.maxEdgeWidth = imaxedgeWidth;
        this.maxWeight = imaxWeight;
        this.minWeight = iminWeight;
        this.maxNodeSize = imaxNodeSize;
        this.maxLength = imaxLength;
        this.minLength = iminLength;
        this.loop = iloop;
    }



}
