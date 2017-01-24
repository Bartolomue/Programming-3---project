import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bartek on 1/15/17.
 */
public class SampleData {

    public static List<Note> docs = new ArrayList<Note>();

    private static final List<String> sampleTopics = Arrays.asList("History", "Ancient_Greece", "Computer_science",
            "Logic", "Mathematics", "Pattern", "Warsaw", "Boston", "New_York", "London", "Kraków", "Math",
            "Information", "Bernoulli_number", "Binary_number");
    private static final String sampleUrl = "https://en.wikipedia.org/wiki/";
    private static final String sampleFolderPath = "data" + java.io.File.separator + "Files";

    public static void createFilesFromWeb() throws IOException {
        for (String topic : sampleTopics) {
            try {
                TextFile.createFile(TextFile.buildPath(sampleFolderPath, topic), TextFile.getContentSite(sampleUrl + topic));
                System.out.println("Text file created.");
            } catch (Exception c) {
                System.out.println("Such topic probably does not exist.");
            }
        }
    }
    public static void main(String[] args)
    {
        /*
        try {
            List<Note> bob = getSampleNotesFromFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }


    /**
     * getSampleNotesFromFiles - performs RAKE algorithm (extractor.java)
     * Entire process is processed on a remote server, with given IP and PORT number.
     *
     * @return
     * @throws IOException
     */
    public static List<Note> getSampleNotesFromFiles(String IP, int portNumber) throws IOException {
        List<Note> notes = new ArrayList<>();

        try {
            for (String topic : sampleTopics) {
                notes.add(new Note(Paths.get(TextFile.buildPath(sampleFolderPath, topic))));
            }
        } catch (Exception e) {
            System.out.println("Note creation error.");
        }

        // Before returning, add terms usually created in Note::Constructor
        // via server connection
        //this.keywords = Extractor.getKeywords("data/Dictionaries/SmartStoplist.txt", this.content);
        if(IP.length() == 0)
        {
            // error block, bad ip
        }
        String givenIP = IP;

        // givenIP = "192.168.1.5"
        // portNumber = 6066;
        notes=serverConnection(givenIP, portNumber, notes);
        // change IP to either passed, or pre-given test?
        // allow port specification, with pre-defined in case of none passed

        //
        return notes;
    }

    /**
     * Server Connection -
     * @param IP
     * @param notes
     * @return
     */
    private static List<Note> serverConnection(String IP, int portNumber, List<Note> notes)
    {
        String serverName = IP;
        List<Note> newNotes = new ArrayList<>();
        int port = portNumber;
        if(portNumber == -1)
        {
            port = 6066;
        }
        //port = 6066;
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket clientSocket = new Socket(serverName, port);

            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());


            System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
            //OutputStream outToServer = clientSocket.getOutputStream();
            //DataOutputStream out = new DataOutputStream(outToServer);

            //out.writeUTF("Hello from " + client.getLocalSocketAddress());
            //InputStream inFromServer = client.getInputStream();
            //DataInputStream in = new DataInputStream(inFromServer);
            outToServer.writeObject(notes);


            newNotes = (List<Note>)inFromServer.readObject();

            for(Note e:newNotes)
            {
                System.out.println("Next keyword!");
            }
            //System.out.println("Server says " + in.readUTF());
            clientSocket.close();
            return newNotes;
        }catch(IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newNotes;
    }

}
