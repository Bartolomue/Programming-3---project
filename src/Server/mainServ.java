package Server;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;

public class mainServ extends Thread {
    private ServerSocket serverSocket;

    public mainServ() throws IOException {
        short port = 6066;
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setSoTimeout(0);
    }

    public void run() {
        while(true) {
            try {
                System.out.println("Waiting for client on port " + this.serverSocket.getLocalPort() + "...");
                Socket e = this.serverSocket.accept();
                ObjectOutputStream outToClient = new ObjectOutputStream(e.getOutputStream());
                ObjectInputStream inFromClient = new ObjectInputStream(e.getInputStream());
                System.out.println("Just connected to " + e.getRemoteSocketAddress());
                ArrayList retNotes = new ArrayList();

                try {
                    retNotes = (ArrayList)inFromClient.readObject();
                } catch (ClassNotFoundException var7) {
                    System.out.println("Class not found exc.");
                    var7.printStackTrace();
                }

                Note e2;
                for(Iterator e1 = retNotes.iterator(); e1.hasNext(); e2.keywords = Extractor.getKeywords("data/Dictionaries/SmartStoplist.txt", e2.content)) {
                    e2 = (Note)e1.next();
                }

                outToClient.writeObject(retNotes);
                e.close();
                continue;
            } catch (SocketTimeoutException var8) {
                System.out.println("Socket timed out!");
            } catch (IOException var9) {
                var9.printStackTrace();
            }

            return;
        }
    }

    public static void main(String[] args) {
        System.out.println("Java works!");

        try {
            mainServ e = new mainServ();
            e.start();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }
}
