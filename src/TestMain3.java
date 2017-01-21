import java.io.IOException;

/**
 * Created by bartek on 1/21/17.
 */
public class TestMain3 {

    public static void main(String argv[]) throws IOException {
        CosineTextSimilarity c = new CosineTextSimilarity("name", "name a");
        double test = c.getCosineSimilarity();
        System.out.println(test);
    }
}
