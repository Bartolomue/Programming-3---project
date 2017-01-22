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

        Visualization v = new Visualization(d.docs, 0.87);

    }
}
