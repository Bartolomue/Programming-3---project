import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by bartek on 1/21/17.
 */
public class Visualization {

    public static Graph graph;
    public static List<Note> vertices;
    public static Set<UnorderedPair<Note>> edges;
    public static Double threshold;

    public Visualization(List<Note> _notes, double _threshold) throws IOException {
        this.vertices = generateVertices(_notes);
        this.threshold = _threshold;
        this.edges = generateEdges(this.vertices, this.threshold);
        drawGraph();
    }

    public static void drawGraph() {
        graph = new SingleGraph("Test");

        Integer i = 0;
        for (Note n : vertices) {
            graph.getNode(n.name);
        }

        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("AB", "A", "B");

        graph.display();
    }

    //remove notes with empty content
    public static List<Note> generateVertices(List<Note> notes) {
        for (Note n : notes) {
            if (n.content.isEmpty() || n.content == "") {
                notes.remove(n);
            }
        }
        return notes;
    }

    public static Set<UnorderedPair<Note>> generateEdges(List<Note> notes, Double threshold) throws IOException {
        if (notes.isEmpty() || (null == threshold)) {
            throw new IllegalArgumentException();
        }

        Set<UnorderedPair<Note>> edges = new HashSet<>();

        for (int i = 0; i < notes.size(); i++) {
            for (int j = 0; j < notes.size(); j++) {
                if (i == j) {
                    continue;
                }
                //double check for notes with empty content
                if (!notes.get(i).content.isEmpty() && !notes.get(i).content.isEmpty()) {
                    edges.add(new UnorderedPair<>(notes.get(i), notes.get(j)));
                }
            }
        }

        Iterator<UnorderedPair<Note>> itEdges = edges.iterator();
        while (itEdges.hasNext()) {
            Iterator<Note> it = itEdges.next().set.iterator();
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

            if (!(CosineTextSimilarity.getCosineSimilarity(n1.content, n2.content) > threshold)) {
                itEdges.remove();
            }
        }

        return edges;
    }





}
