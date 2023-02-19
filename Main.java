import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;

public class Main{
    private static int PORT = 4901;

    // create arrayList for client connections
    protected static ArrayList<ConnectionFromClient> connections = new ArrayList<ConnectionFromClient>();
    public static void main(String[] args){
        try {
            try (// start the server
            ServerSocket serverSocket = new ServerSocket(PORT)) {
                Logger.log("SERVER SOCKET UP on PORT "+PORT);
                // wait for connections
                while(true){
                    Socket newSocket = serverSocket.accept(); // waits for next connection
                    ConnectionFromClient newConnection = new ConnectionFromClient(newSocket); // creates a connetion object (extends Thread) to run the connection
                    connections.add(newConnection); // adds the object to the list of connections 
                    newConnection.start(); // starts the thread
                    
                    // Debug
                    Logger.log("CURRENT CONNECTIONS: "+connections.size());
                }
            }
        } catch (IOException e) {
            Logger.log(e.getMessage());
        }
    }
}