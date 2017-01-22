import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import java.util.*;

/**
 * Created by Maksym on 2017-01-13.
 */
public class sGraph {
    private Graph internalGraph;
    private LinkedList<ContainerEdge> edges;

    public sGraph()
    {
        edges = new LinkedList<ContainerEdge>(); // Instantiate! Otherwise .add doesn't work
        internalGraph = new SingleGraph("internalGraphID");
    }

    private class ContainerEdge
    {
        public String sourceId;
        public String destinationId;
        public double originalConnectionStrength;
        public double normalisedConnectionStrength;

        ContainerEdge(){}
        ContainerEdge(String sId,String dId, double strength)
        {
            this.sourceId = sId;
            this.destinationId = dId;
            this.originalConnectionStrength = strength;
        }

    }

    private void createGraph()
    {
        for(ContainerEdge e: )
    }

    public void giveEdge(String key, String sourceId, String destinationId)
    {
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
        normalizeEdgeList();
        //
        for(ContainerEdge e : edges)
        {
            System.out.println(e.normalisedConnectionStrength);
        }
        internalGraph.display();
    }

    private double cosineSimilarity(double[] vectorA, double[] vectorB)
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

    private double createConnectionFiles(Map<String, Double> inputMapA, Map<String,Double> inputMapB, int userSize)
    {
        // Sort input tree map in desc order by key!!!!!
        // Test START
        /*for (Map.Entry<String, Double> entry : inputMapA.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
        for (Map.Entry<String, Double> entry : inputMapB.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }*/
        // Test END

        inputMapA=sortByValue(inputMapA);
        inputMapB=sortByValue(inputMapB);

        // Test START
        /*System.out.println("After sort ---------------------------");
        System.out.println("Map A:");
        for (Map.Entry<String, Double> entry : inputMapA.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
        System.out.println("Map B:");
        for (Map.Entry<String, Double> entry : inputMapB.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }*/
        // Test END
        // Assumes: map is sorted, user picks only TOP values
        int indToUse1=userSize;
        int indToUse2=userSize;
        if(inputMapA.size() < indToUse1) indToUse1 = inputMapA.size();
        if(inputMapB.size() < indToUse2) indToUse2 = inputMapB.size();

        Set<String> set1 = inputMapA.keySet();
        Set<String> set2 = inputMapB.keySet();
        Set<String> finalSet = new HashSet<String>(set1);
        finalSet.addAll(set2); // Adds all not already present in s1

        Double[] resultVectorA = new Double[finalSet.size()];
        Double[] resultVectorB = new Double[finalSet.size()];
        int counter=0;
        for(String x: finalSet)
        {
            // x is current term to check in both file input v's
            if(inputMapA.containsKey(x))
            {
                resultVectorA[counter]=inputMapA.get(x);
            }
            else
            {
                resultVectorA[counter]=0.0;
            }

            if(inputMapB.containsKey(x))
            {
                resultVectorB[counter]=inputMapB.get(x);
            }
            else
            {
                resultVectorB[counter]=0.0;
            }

            counter+=1;
        }
        /* Test of output vectors
        System.out.println();
        for(Double x: resultVectorA)
        {
            System.out.println("VA: " + x);
        }
        System.out.println(resultVectorA.length + "  " + resultVectorB.length);
        for(Double x: resultVectorB)
        {
            System.out.println("VB: " + x);
        }
        */
        double[] resultVectorAdouble = new double[resultVectorA.length];
        double[] resultVectorBdouble = new double[resultVectorB.length];
        for(int i=0; i<resultVectorA.length; i++)
        {
            resultVectorAdouble[i]=resultVectorA[i].doubleValue();
            resultVectorBdouble[i]=resultVectorB[i].doubleValue();
        }

        Double returnVal = cosineSimilarity(resultVectorAdouble, resultVectorBdouble);
        return returnVal;

    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return (0-(o1.getValue()).compareTo( o2.getValue() ));
            }
        } );

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

    private double currentEdgeValSum()
    {
        double result = 0.0;
        for(ContainerEdge e : edges)
        {
            result+=e.originalConnectionStrength;
        }
        return result;
    }

    private void normalizeEdgeList()
    {
        System.out.println();
        double currentSum = currentEdgeValSum();
        for(ContainerEdge e : edges)
        {
            e.normalisedConnectionStrength=e.originalConnectionStrength/currentSum;
            System.out.println("New connection strength: "+e.normalisedConnectionStrength);

        }
    }

    public void AddConnection(Map<String,Double> phraseMap1, String doc1Id, Map<String,Double> phraseMap2, String doc2Id)
    {
        /*
        Map<String,Double> testMap1 = new HashMap();
        testMap1.put("T1", new Double(3.8));
        testMap1.put("T2", 4.9);
        testMap1.put("T3", (double) 99);
        testMap1.put("T9", 3.333);
       // testMap1.put("T1", 2.2);

        Map<String, Double> testMap2 = new HashMap();
        testMap2.put("T1", 4.0);
        testMap2.put("T5", 4.9);
        testMap2.put("T4", 4.4);

        Map<String, Double> testMap3 = new HashMap();
        testMap3.put("T9", 3.3);
        testMap3.put("T11", 1.3);
        */
        int userTop = 3;

        Double cosSim = createConnectionFiles(phraseMap1,phraseMap2,userTop);
        ContainerEdge freshE = new ContainerEdge(doc1Id, doc2Id, cosSim);
        edges.add(freshE);


        System.out.println("Similarity: " + cosSim);
    }

    public void testValues(Data docSet)
    {
        // given a list of documents


        int i=0;
        for(Document d1 : docSet.docs)
        {
            for(Document d2 : docSet.docs.subList(i,docSet.docs.size()))
            {
                if (d1 == d2 || d1.equals(d2))
                    continue;
                else {
                    this.AddConnection(d1.keywords, d1.docId, d2.keywords, d2.docId);
                }
            }
            i++;
        }
        normalizeEdgeList();
    }

}
