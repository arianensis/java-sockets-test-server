import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class ConnectionWindow { // Creates a window to show info of a single connection
    
    final JFrame jFrame; // the window object
    final JPanel jPanel; // the content of the window
    final JLabel jLabel; // the message to show

    public ConnectionWindow(ConnectionFromClient connection) {

        // create the window
        jFrame = new JFrame("Connection #"+connection.id);
        jFrame.setBounds(connection.id*32, connection.id*32, 512, 96);
        jFrame.setVisible(true);
        jFrame.setLayout(null);
        // prevent from resizing or closing
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // create the content and add it to the window
        jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBackground(Color.BLACK);
        jPanel.setVisible(true);
        jPanel.setBounds(0, 0, 512, 64);
        jFrame.add(jPanel);

        // create the text and add it to the content
        jLabel = new JLabel();
        jLabel.setForeground(Color.WHITE);
        jLabel.setVisible(true);
        jLabel.setBounds(16, 16, 480, 32);
        jLabel.setText("Hola");
        jPanel.add(jLabel);
    }
    public void destroy() {
        jFrame.setVisible(false);
        jFrame.dispose();
    }
    public void log(String message) {
        jLabel.setText(message);
        Logger.log(message);
    }
    public JFrame getJFrame(){ return this.jFrame; }
}
