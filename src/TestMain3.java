import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bartek on 1/21/17.
 */
public class TestMain3 {

    public static void main(String argv[]) throws IOException {

        Data d = new Data();
        d.getDataLocalFiles();

        Visualization v = new Visualization(d.docs, 0.85);

//        Set<UnorderedPair<Note>> edges = new HashSet<>();
//
//        for (int i = 0; i < d.docs.size(); i++) {
//            for (int j = 0; j < d.docs.size(); j++) {
//                if (i == j) {
//                    continue;
//                }
//                edges.add(new UnorderedPair<>(d.docs.get(i), d.docs.get(j)));
//            }
//        }
//
//        edges.add(new UnorderedPair<>(null, null));

//        System.out.println(CosineTextSimilarity.getCosineSimilarity("", ""));

//        System.out.println(edges.size());





//        System.out.println(s1);
//        System.out.println(s2);
//
//        CosineTextSimilarity c = new CosineTextSimilarity("b a", "a b");
//        double test = c.getCosineSimilarity();
//        System.out.println(test);
    }
}
