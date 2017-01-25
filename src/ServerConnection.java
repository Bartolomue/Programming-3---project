import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maksym
 */
public class ServerConnection {
    public String IP;
    public int PORT;

    ServerConnection(){}

    ServerConnection(String iIp, int iport)
    {
        IP = iIp;
        PORT = iport;
    }

    public ArrayList<Note> performTransfer(ArrayList<Note> notes)
    {
        // if PORT field is empty -> cal with -1
        if(PORT == -1)
        {
            PORT = 6066;
        }
        ArrayList<Note> newNotes = new ArrayList<>();
        //port = 6066;
        try {
            System.out.println("Connecting to " + IP + " on port " + PORT);
            Socket clientSocket = new Socket(IP, PORT);

            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());

            outToServer.writeObject(notes);

            newNotes = (ArrayList<Note>)inFromServer.readObject();

            for(Note e:newNotes)
            {
                System.out.println("Next keyword!");
            }

            clientSocket.close();

        }catch(IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return newNotes;
    }
}
