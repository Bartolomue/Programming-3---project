import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by bwichowski on 2017-01-24.
 */
class TestGui extends JFrame {
    JPanel panel;
    JLabel label;

    // constructor
    TestGui( String title ) {
        super( title );                      // invoke the JFrame constructor
        setSize( 2000, 2000);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        setLayout(new BorderLayout());
        label = new JLabel("Hello Swing!");  // construct a JLabel
        add( label );                        // add the label to the JFrame
    }

}

class TestFrame2 {
    public static void main ( String[] args ) throws IOException, InterruptedException {
        Visualization v = new Visualization(SampleData.getSampleNotesFromFiles(), 0.84, 3);
        Viewer viewer = new Viewer(v.getGraph(), Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);

        View view = viewer.addDefaultView(false);
        viewer.enableAutoLayout();

        TestGui frame = new TestGui("Hello"); // construct a MyFrame object
        frame.getContentPane().add((Component) view, BorderLayout.CENTER);
//        frame.getContentPane().add((Component) view, BorderLayout.PAGE_END);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.pack();

        frame.setVisible( true );             // ask it to become visible
    }
}