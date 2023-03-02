import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//import javax.swing.Timer;

public class ConnectionFromClient extends Thread {
    // CLASS VARIABLES
    static int nextId = 0; // will be used to autoincrement and give unique id to objects

    // OBJECT ATTRIBUTES
    final int id; // the id that will be assigned to the current object
    final Socket socket; // the socket which connects from the client
    DataInputStream in; // to receive data from client and/or know if it's still connected
    DataOutputStream out; // to send data to client for read confirmation
    final ConnectionWindow window; // the window to show info about only this connection

    //final Timer periodicConnectionCkeck;
    
    // CONSTRUCTOR
    public ConnectionFromClient(Socket socket){
        this.socket = socket;
        this.id = ++nextId; // increments class variable for next id and assigns result to object id
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
        }
        // Create window
        window = new ConnectionWindow(this);

        // Debug connection info        
        window.log("NEW CONNECTION with ID="+this.id+" from ADDR="+socket.getRemoteSocketAddress().toString());
    }
    // THREAD
    public void run() {        
        //periodicConnectionCkeck = new Timer(1000, new ActionListener() { public void actionPerformed(ActionEvent e) {if (true) this.delete();}}
        startRead(); // Starts to read incoming data
    }

    // METHODS
    public void delete() { // closes the socket and removes the connection from the global list
        try {
            socket.close();
            Main.connections.remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startRead() { //constantly reads incoming data until the client disconnects
        while (true) {
            try {
                int response = in.read();
                if (response==-1) { // -1 means the connection is closed, otherwise it just reads nothing
                    closeConnection();
                    return;
                } else { // if the response is a natural number, it means it's data from the client. Print the data received
                    window.log("CONNECTION with ID="+this.id+" from ADDR="+socket.getRemoteSocketAddress().toString()+" RECEIVED: "+response);
                    // answer with a 1 so the client can know the server received the message
                    out.writeByte(1);
                }
            } catch (IOException e) {
                closeConnection();
                return;
            }
        }
    }
    public void closeConnection() {
        window.log("CONNECTION with ID="+this.id+" from ADDR="+socket.getRemoteSocketAddress().toString() + " CLOSED");
        window.destroy();
        this.delete();
        window.log("CURRENT CONNECTIONS: "+Main.connections.size());
    }
}
