import java.io.IOException;

/**
 * Created by bartek on 1/21/17.
 */
public class TestMain3 {

    public static void main(String argv[]) throws IOException {

        Data d = new Data();
        d.getDataLocalFiles();

        String s1 = d.docs.get(0).content;
        String s2 = d.docs.get(1).content;

        System.out.println(s1);
        System.out.println(s2);

        CosineTextSimilarity c = new CosineTextSimilarity("b a", "a b");
        double test = c.getCosineSimilarity();

        System.out.println(test);
    }
}
