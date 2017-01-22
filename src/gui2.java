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
import java.io.PrintWriter;
import java.util.*;


public class gui2 extends JFrame implements ActionListener{

    private JMenuBar menuBar;
    private JMenu menuFile,  menuHelp;
    private JMenuItem mOpen, mClose, mSave,mAbout;
    private JTextArea notatnik;
    private JScrollPane scrollpane;

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
        mClose.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));  //Allows closing program by pressing combination Ctrl+x


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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == mClose)
        {
            int odpowiedz = JOptionPane.showConfirmDialog(this, "Close? ","Close", JOptionPane.YES_NO_OPTION);

            if(odpowiedz == JOptionPane.YES_OPTION)
                dispose();

        }
        if (source == mAbout)
        {
            JOptionPane.showMessageDialog(this, " Mikołaj Kida, \n Maks Stec, \n Bartłomiej Wichowski, \n Piotr Radomski","Authors:",JOptionPane.INFORMATION_MESSAGE);
        }
        if (source == mOpen)        //After clicking "Open" button
        {
            JFileChooser fc = new JFileChooser();
            if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)  //wybrany plik + nacisniety przcisk "OK"
            {
                File myfile = fc.getSelectedFile();
                //JOptionPane.showMessageDialog(null, "Chosen file is "+myfile.getPath());
                try {
                    Scanner scaner = new Scanner(myfile);
                    while(scaner.hasNext())
                    {
                        notatnik.append(scaner.nextLine() +"\n");
                    }
                }
                catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

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
