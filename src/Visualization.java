import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by bartek on 1/21/17.
 */
public class Visualization implements ViewerListener {

    public static Graph graph;
    public static ViewPanel view;
    public static Map<String, Note> vertices;
    public static Map<String, String> verticesNames;
    public static Map<String, UnorderedPair<Note>> edges;
    public static Map<String, Double> edgesWeights;
    public static Double threshold;
    public static Integer keywordsNumber;
    //public static Integer modeIndicator; // 1=load, 2=save, 3=regular

    protected boolean loop = true;

    public Visualization()
    {
        this.graph = getGraph();
    }

    public Visualization(List<Note> _notes, double _threshold, int _keywordsNumber) throws IOException {
        this.threshold = _threshold;
        this.vertices = generateVertices(_notes);
        this.verticesNames = getVerticesNames(this.vertices);
        this.edges = generateEdges(this.vertices);
        this.edgesWeights = getEdgesWeights(this.edges);
        this.keywordsNumber = _keywordsNumber;
    }

    public static void main(String argv[]) {
        /*
        String fileName = "bob.bin";
        File file = new File("./data/Saves/"+fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done");
        */
    }

    public void drawGraph() throws InterruptedException {
        graph = getGraph();
        //saveGraph("testLocation");
        for (String s : vertices.keySet()) {

            String label = verticesNames.get(s) + ": ";

            Integer i = 0;
            List<String> keywords = new ArrayList<String>();
            for (String keyword : vertices.get(s).keywords.keySet()) {
                if (i > keywordsNumber) {
                    break;
                }
                keywords.add(keyword);
                i++;
            }

            label += String.join(", ", keywords);

            graph.addNode(s).addAttribute("ui.label", label);
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

        Viewer viewer = graph.display();

        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

        ViewerPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addViewerListener(this);
        fromViewer.addSink(graph);


        view = viewer.getDefaultView();

        while(loop) {
            fromViewer.blockingPump(); // or fromViewer.blockingPump(); in the nightly builds

            // here your simulation code.

            // You do not necessarily need to use a loop, this is only an example.
            // as long as you call pump() before using the graph. pump() is non
            // blocking.  If you only use the loop to look at event, use blockingPump()
            // to avoid 100% CPU usage. The blockingPump() method is only available from
            // the nightly builds.
        }

    }

    public static ViewPanel getView() {
        return view;
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
            GraphSerialized graphDataOut = new GraphSerialized(vertices,verticesNames,edges,edgesWeights,threshold, keywordsNumber);
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
            String fileName = ilocation + ".bin";
            File loadSource = new File("./data/Saves/"+fileName);
            ObjectInputStream is =
                    new ObjectInputStream(new FileInputStream(loadSource));
            GraphSerialized loadGraph = (GraphSerialized) is.readObject();

            vertices = loadGraph.vertices;
            verticesNames=loadGraph.verticesNames;
            edges = loadGraph.edges;
            edgesWeights = loadGraph.edgesWeights;
            threshold = loadGraph.threshold;
            keywordsNumber = loadGraph.keywordsNumber;
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
