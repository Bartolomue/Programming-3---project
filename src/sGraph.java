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

    public void giveEdge(String key, String sourceId, String destinationId)
    {
        //internalGraph.addNode(key);
        internalGraph.addEdge(sourceId+destinationId, sourceId, destinationId);
        System.out.println("Edge " + key + "has been added/n");
    }

    public void giveNode(String key)
    {
        internalGraph.addNode(key);
        System.out.println("Node " + key + "has been added/n");
    }

    public void displayGraph()
    {
        internalGraph.display();
    }

    public double cosineSimilarity(int[] vectorA, int[] vectorB)
    {
        double result=0.0;
        double dotProduct=0.0;
        double normA=0.0, normB=0.0;
		/*
		 * cosine(alfa) = A . B / ||A|| ||B||
		 * FOR vector A=(a1,a2), ||A|| is defined as sqrt(a1^2+a2^2)
		 * FOR vector A=(a1,a2), B=(b1,b2), A.B is defined as a1*b1+a2*b2
		 * therefore cosine sim is (a1*b1+a2*b2)/sqrt(a1^2+a2^2)*sqrt(b1^2+b2^2)
		 *
		 */

        // Assume A and B have equal length, even if scarce
        for(int i=0; i<vectorA.length; i++)
        {
            dotProduct+=vectorA[i]*vectorB[i]; //aK*bK
            normA+=Math.pow(vectorA[i], 2); // Math contains better implementa
            normB+=Math.pow(vectorB[i], 2);
        }
        result=dotProduct / (Math.sqrt(normA)*Math.sqrt(normB));

        return result;
    }

    //int[] vectorA, int[] vectorB
    public void testValues(int[] vectorA, int[] vectorB)
    {
        //int[] t1 = new int[5];
        int[] t1 = {2,3,4,5,6};
        int[] t2 = {1,2,3,4,5};
        double r1 = cosineSimilarity(vectorA,vectorB);
        System.out.println("vA: "+toStringI(vectorA));
        System.out.println("vB: "+toStringI(vectorB));
        System.out.println("Result: " + r1);
    }

    private String toStringI(int[] vector)
    {
        String retVal = "[";
        for(int i=0;i<vector.length-1;i++)
        {
            retVal+=vector[i];
            retVal+=", ";
        }
        retVal=retVal.substring(0,retVal.length()-2);
        retVal+="]";
        return retVal;
    }
}
