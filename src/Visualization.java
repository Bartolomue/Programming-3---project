import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.ViewerListener;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by bartek on 1/21/17.
 */
public class Visualization implements ViewerListener {

    public Graph graph;
    public Map<String, Note> vertices;
    public Map<String, String> verticesNames;
    public Map<String, UnorderedPair<Note>> edges;
    public Map<String, Double> edgesWeights;
    public Double threshold;
    public Integer keywordsNumber;
    private static Integer maxEdgeWidth = 4;
    private static Double maxWeight;
    private static Double minWeight;

    private static Integer maxNodeSize = 24;
    private static Integer maxLength;
    private static Integer minLength;

    //public static Integer modeIndicator; // 1=load, 2=save, 3=regular

    protected boolean loop = true;

    public Visualization(List<Note> _notes, double _threshold, int _keywordsNumber) throws IOException, InterruptedException {
        this.threshold = _threshold;
        this.vertices = generateVertices(_notes);
        this.verticesNames = getVerticesNames(this.vertices);
        this.edges = generateEdges(this.vertices);
        this.edgesWeights = getEdgesWeights(this.edges);
        this.maxWeight = Collections.max(this.edgesWeights.values());
        this.minWeight = Collections.min(this.edgesWeights.values());
        this.keywordsNumber = _keywordsNumber;
        this.maxLength = getMaxLenghtNote(this.vertices);
        this.minLength = getMinLenghtNote(this.vertices);
        this.graph = createGraph();
    }

    public Visualization() {

    }

    public Graph getGraph() {
        return graph;
    }

    /**
     * SaveGraph - saves graph configuration to a predefined 'location'.
     Serialization process includes creation of GraphSerialized class
     object.
     Due to GraphStreamProject.Graph implementation using streams,
     serialization process excludes the Graph object itself.
     Errors:
     - invalid file name provided
     * @param location
     */
    public void saveGraph(String location) {
        if(location == null)
        {
            // error pop!!!
            System.out.println("No destination file!");
            return;
        }
        else
        {
            GraphSerialized graphDataOut = new GraphSerialized(vertices,verticesNames,edges,edgesWeights,threshold, keywordsNumber, maxEdgeWidth, maxWeight, minWeight,maxNodeSize,maxLength,minLength,loop);
            String fileName = location + ".bin";
            File saveDestination = new File("./data/Saves/"+fileName);
            try {
                // create actual .bin file
                saveDestination.createNewFile();
            } catch (IOException e) {
                System.out.println("File creation error");
                e.printStackTrace();
            }
            try
            {
                ObjectOutputStream os =
                        new ObjectOutputStream(new FileOutputStream(saveDestination));
                os.writeObject(graphDataOut);
                os.close();

            }catch(IOException e)
            {
                // error pop!!!
                System.out.println("ObjectOutputStream error!");
            }
        }
    }

    /**
     * LoadGraph - allows loading previously stored data configuration.
     Argument passed must be a defined previously stored .bin graph config file.
     Destination folder is not definable by user.

     Requirements:
     - new Visualisation class with Graph object initialized.
     - loaded configuration with the same keywords set

     Error:
     - location doesn't exist
     * @param ilocation
     * @throws InterruptedException
     */
    public void loadGraph(String ilocation) throws InterruptedException {
        try
        {
            String fileName = ilocation + ""; // + .bin
            File loadSource = new File("./data/Saves/"+fileName);
            ObjectInputStream is =
                    new ObjectInputStream(new FileInputStream(loadSource));
            GraphSerialized loadGraph = (GraphSerialized) is.readObject();

            this.vertices = loadGraph.vertices;
            this.verticesNames=loadGraph.verticesNames;
            this.edges = loadGraph.edges;
            this.edgesWeights = loadGraph.edgesWeights;
            this.threshold = loadGraph.threshold;
            this.keywordsNumber = loadGraph.keywordsNumber;
            this.maxEdgeWidth = loadGraph.maxEdgeWidth;
            this.maxWeight = loadGraph.maxWeight;
            this.minWeight = loadGraph.minWeight;
            this.maxNodeSize = loadGraph.maxNodeSize;
            this.maxLength = loadGraph.maxLength;
            this.minLength = loadGraph.minLength;
            this.loop = loadGraph.loop;
            this.graph = createGraph();
            //System.out.println("Loaded graph: "+loadMan.name);

            is.close();

            //drawGraph();
        }catch(IOException e)
        {
            // error pop
            System.out.println("IO error while loading - file doesnt exist?");

        } catch (ClassNotFoundException e) {
            // error pop
            e.printStackTrace();
            System.out.println("ClassNotFound - deserialisation while loading!");

        }
    }

    private Graph createGraph() throws InterruptedException {

        Graph graph = new SingleGraph("Test");
        graph.addAttribute("ui.stylesheet", "url('data/Styles/graph.css')");
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");

        for (String s : vertices.keySet()) {

//            String label = verticesNames.get(s) + ": ";
//
//            Integer i = 0;
//            List<String> keywords = new ArrayList<>();
//            for (String keyword : vertices.get(s).keywords.keySet()) {
//                if (i > keywordsNumber) {
//                    break;
//                }
//                keywords.add(keyword);
//                i++;
//            }
//
//            label += String.join(", ", keywords);

            graph.addNode(s).addAttribute("ui.label", vertices.get(s).name);
            graph.getNode(s).addAttribute("ui.size", 2 + ((vertices.get(s).content.length() - minLength) /
                    (maxLength - minLength) + 1 + 0.15) * maxNodeSize);
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



                graph.addEdge(s, n1.id, n2.id).addAttribute("ui.size", 1 + (int)
                        ((edgesWeights.get(s) - minWeight) /
                                ((maxWeight - minWeight)) * maxEdgeWidth));

            }
        }

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

    private Integer getMaxLenghtNote(Map<String, Note> notes) {
        Integer size = 0;
        for (Note s : notes.values()) {
            if (s.content.length() > size) {
                size = s.content.length();
            }
        }

        return size;
    }

    private Integer getMinLenghtNote(Map<String, Note> notes) {
        Integer size = Integer.MAX_VALUE;
        for (Note s : notes.values()) {
            if (s.content.length() < size) {
                size = s.content.length();
            }
        }

        return size;
    }

    public void viewClosed(String id) {
        loop = false;
    }

    public void buttonPushed(String id) {
        System.out.println("Button pushed on node "+id);
        Editor e = new Editor(vertices.get(id));
        JFrame f = new JFrame(verticesNames.get(id));
        f.setMinimumSize(new Dimension(530, 330));
        f.add(e);
        f.setVisible(true);
    }

    public void buttonReleased(String id) {
        System.out.println("Button released on node "+id);
    }
}
