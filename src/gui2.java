import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.nio.file.Path;

public class gui2 extends JFrame implements ActionListener{

    private JMenuBar menuBar;
    private JMenu menuFile,  menuHelp;
    private JMenuItem mOpen, mClose, mSave,mAbout;
    private JTextArea notatnik;
    private JScrollPane scrollpane;
    private JFileChooser chooser;
    String choosertitle;
    ArrayList<Note> note = new ArrayList();

    public gui2(){
        setTitle("MineYourNotes");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        menuBar = new JMenuBar();
        menuFile = new JMenu("File");

        menuHelp = new JMenu("Help");

        mOpen = new JMenuItem("Open");
        mOpen.addActionListener(this);


        mSave = new JMenuItem("Save");
        mSave.addActionListener(this);

        mClose = new JMenuItem("Close");

        menuFile.add(mOpen);
        menuFile.add(mSave);
        menuFile.addSeparator();
        menuFile.add(mClose);

        mClose.addActionListener(this);
        mClose.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));  //Allows to close a program by the combination Ctrl+x


        mAbout = new JMenuItem("About");
        mAbout.addActionListener(this);
        menuHelp.add(mAbout);

        setJMenuBar(menuBar);
        menuBar.add(menuFile);
	/*
		notatnik = new JTextArea();
		scrollpane = new JScrollPane(notatnik);
		scrollpane.setBounds(50, 50, 500, 300);
		add(scrollpane);
		*/



        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(menuHelp);

    }

    public void listFilesForFolder(final File folder) throws IOException {             //takes all .txt files from a given folder and its subfolders
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else if (fileEntry.getName().endsWith(".txt")){
                //System.out.println(fileEntry.getName());
                // tu wywolac funkcje
              // System.out.println("getSelectedFile() : " +  fileEntry.getAbsolutePath());
                System.out.println("Chosen file: " +  fileEntry.getName());
               Path path;
               path = fileEntry.toPath();
                //Note note = new Note(path);
                note.add(new Note(path));
            }
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == mClose)
        {
            int respond = JOptionPane.showConfirmDialog(this, "Close? ","Close", JOptionPane.YES_NO_OPTION);

            if(respond == JOptionPane.YES_OPTION)
                dispose();

        }
        if (source == mAbout)   //if "About" button was chosen
        {
            JOptionPane.showMessageDialog(this, " Mikołaj Kida, \n Maks Stec, \n Bartłomiej Wichowski, \n Piotr Radomski","Authors:",JOptionPane.INFORMATION_MESSAGE);
        }
        if (source == mOpen)        //After clicking "Open" button
        {

            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle(choosertitle);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            //
            // disable the "All files" option.
            //
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
               // System.out.println("Current directory: " +  chooser.getCurrentDirectory());
                System.out.println("Files will be chosen from the directory : " +  chooser.getSelectedFile());
            }
            else {
                System.out.println("No Selection ");
            }

            try {
                listFilesForFolder(chooser.getSelectedFile());
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }


	/*	else if (source == mSave)
		{
			JFileChooser fc2 = new JFileChooser();
			if(fc2.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
			{
				File myfile2 = fc2.getSelectedFile();
				//JOptionPane.showMessageDialog(null, "Chosen file is "+myfile2.getPath());
				try {
					PrintWriter pw = new PrintWriter(myfile2);
					Scanner scaner = new Scanner(notatnik.getText());
					while(scaner.hasNext())
						pw.println(scaner.nextLine()+"\n");

					pw.close();
				}
				catch (FileNotFoundException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}
		*/
    }

    public static void main(String[] args) {
        gui2 nowy = new gui2();
        nowy.setVisible(true);
    }


}
