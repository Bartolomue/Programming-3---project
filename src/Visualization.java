import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import java.io.IOException;
import java.util.*;

/**
 * Created by bartek on 1/21/17.
 */
public class Visualization {

    public static Graph graph;
    public static Map<String, Note> vertices;
    public static Map<String, String> verticesNames;
    public static Map<String, UnorderedPair<Note>> edges;
    public static Map<String, Double> edgesWeights;
    public static Double threshold;

    public Visualization(List<Note> _notes, double _threshold) throws IOException {
        this.threshold = _threshold;
        this.vertices = generateVertices(_notes);
        this.verticesNames = getVerticesNames(this.vertices);
        this.edges = generateEdges(this.vertices);
        this.edgesWeights = getEdgesWeights(this.edges);
    }

    public static void main(String argv[]) {
        drawGraph();
    }

    public static void drawGraph() {
        graph = getGraph();

        for (String s : vertices.keySet()) {
            graph.addNode(s).addAttribute("ui.label", verticesNames.get(s));
        }

        for (String s : edges.keySet()) {
            if (edgesWeights.get(s) > threshold) {
                Iterator<Note> it = edges.get(s).set.iterator();
                Note n1 = null;
                Note n2 = null;

                if (it.hasNext()) {
                    n1 = it.next();
                }

                if (it.hasNext()) {
                    n2 = it.next();
                }
                if (n1 != null && n2 != null) {
                    System.out.println("Ok");
                } else {
                    throw new IllegalArgumentException("Notes should be paired in order to create edge.");
                }
                graph.addEdge(s, n1.id, n2.id);
            }
        }

        graph.display();
    }

    private static Graph getGraph() {
        Graph graph = new SingleGraph("Test");
        graph.addAttribute("ui.stylesheet", "url('data/Styles/graph.css')");
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        return graph;
    }

    private static Map<String, Note> generateVertices(List<Note> notes) {

        if (notes.isEmpty()) {
            throw new IllegalArgumentException("List is empty.");
        }

        Map<String, Note> notesWithContent = new HashMap<>();

        for (Note n : notes) {
            if (!(n.content.isEmpty() || n.content == "")) {
                if (!notesWithContent.containsKey(n.id)) {
                    notesWithContent.put(n.id , n);
                }
            }
        }

        return notesWithContent;
    }


    private static Map<String, String> getVerticesNames(Map<String, Note> vertices) {
        if (vertices.isEmpty()) {
            throw new IllegalArgumentException("Map is empty.");
        }

        Map<String, String> notesNames = new HashMap<>();

        for (String s : vertices.keySet()) {
            if (!notesNames.containsKey(s)) {
                notesNames.put(s, vertices.get(s).name);
            }
        }

        return notesNames;
    }

    private static Map<String, UnorderedPair<Note>> generateEdges(Map<String, Note> id2Note) {
        if (id2Note.isEmpty()) {
            throw new IllegalArgumentException("Map is empty.");
        }

        Set<UnorderedPair<Note>> allEdges = new HashSet<>();

        for (Note n1 : id2Note.values()) {
            for (Note n2 : id2Note.values()) {
                if (n1 == n2) {
                    continue;
                }
                allEdges.add(new UnorderedPair<>(n1, n2));
            }
        }

        Map<String, UnorderedPair<Note>> id2Edge = new HashMap<>();

        for (UnorderedPair p : allEdges) {
            String uniqueId = UUID.randomUUID().toString();
            id2Edge.put(uniqueId, p);
        }

        return id2Edge;
    }

    private static Map<String, Double> getEdgesWeights(Map<String, UnorderedPair<Note>> edges)
            throws IOException {

        if (edges.isEmpty()) {
            throw new IllegalArgumentException("Map is empty.");
        }

        Map<String, Double> edgesWeights = new HashMap<>();

        for (String s : edges.keySet()) {
            Iterator<Note> it = edges.get(s).set.iterator();
            Note n1 = null;
            Note n2 = null;

            if (it.hasNext()) {
                n1 = it.next();
            }

            if (it.hasNext()) {
                n2 = it.next();
            }
            if (n1 != null && n2 != null) {
                System.out.println("Ok");
            } else {
                throw new IllegalArgumentException("Notes should be paired in order to create edge.");
            }

            Double similarity = CosineTextSimilarity.getCosineSimilarity(n1.content, n2.content);
            edgesWeights.put(s, similarity);
        }

        return edgesWeights;
    }
}
