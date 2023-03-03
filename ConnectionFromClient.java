import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionFromClient extends Thread {
    // CLASS VARIABLES
    static int nextId = 0; // will be used to autoincrement and give unique id to objects

    // OBJECT ATTRIBUTES
    final int id; // the id that will be assigned to the current object
    final Socket socket; // the socket which connects from the client
    ObjectInputStream in; // to receive data from client and/or know if it's still connected
    ObjectOutputStream out; // to send data to client for read confirmation
    final ConnectionWindow window; // the window to show info about only this connection

    //final Timer periodicConnectionCkeck;
    
    // CONSTRUCTOR
    public ConnectionFromClient(Socket socket){
        this.socket = socket;
        this.id = ++nextId; // increments class variable for next id and assigns result to object id
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
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
                Message response = (Message) in.readObject(); // cast response to Message because it could be anything
                if (response==null) { // the object is null if connection is closed
                    closeConnection();
                    return;
                } else { // if the response is an object, it means it's data from the client. Print the data received
                    window.log("CONNECTION with ID="+this.id+" from ADDR="+socket.getRemoteSocketAddress().toString()+" RECEIVED: "+response);
                    // if x and y are defined (not -1) move the window to those coordinates
                    if (response.getX()>-1 && response.getY()>-1) window.getJFrame().setBounds(response.getX(), response.getY(), 512, 96);

                    // answer with the same object so the client can know the server received the message
                    out.writeObject(response);
                }
            } catch (Exception e) {
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
