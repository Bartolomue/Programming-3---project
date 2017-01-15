import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import java.util.*;

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

    public double cosineSimilarity(double[] vectorA, double[] vectorB)
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

        //double r1 = cosineSimilarity(vectorA,vectorB);
        System.out.println("vA: "+toStringI(vectorA));
        System.out.println("vB: "+toStringI(vectorB));
        //System.out.println("Result: " + r1);
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

    public void createConnectionFiles(Map<String, Double> inputMapA, Map<String,Double> inputMapB, int userSize)
    {
        // Sort input tree map in desc order by key!!!!!
        // Test START
        for (Map.Entry<String, Double> entry : inputMapA.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
        for (Map.Entry<String, Double> entry : inputMapB.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
        // Test END

        inputMapA=sortByValue(inputMapA);
        inputMapB=sortByValue(inputMapB);

        // Test START
        System.out.println("After sort ---------------------------");
        System.out.println("Map A:");
        for (Map.Entry<String, Double> entry : inputMapA.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
        System.out.println("Map B:");
        for (Map.Entry<String, Double> entry : inputMapB.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
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

        System.out.println("Similarity: " + returnVal);

    }

    public static <K, V extends Comparable<? super V>> Map<K, V>
    sortByValue( Map<K, V> map )
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

    public void testConnection(Map<String,Double> City1, Map<String,Double> City2)
    {
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

        int userTop = 3;

        createConnectionFiles(City1,City2,userTop);
    }

}
