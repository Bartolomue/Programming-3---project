/**
 * Created by bartek on 11/6/16.
 */

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;

public class TestMain1 {

    public static void main(String[] args) throws IOException {
        Note n = new Note(Paths.get("data/Files/Warsaw"));
        Editor e = new Editor(n);
        JFrame f = new JFrame();
        f.setMinimumSize(new Dimension(530, 330));
        f.add(e);
        f.setVisible(true);
    }
}
