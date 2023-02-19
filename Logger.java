import java.util.Date;

public class Logger { // class to manage console logs

    static boolean isEnabled = true; // variable to determine if the logs will be shown

    public static void log(String message) { // will log the message with the current timestamp and a separation       
        if (isEnabled) {
            System.out.println();
            Date timestamp = new Date();
            System.out.println(timestamp + " --> " + message);
        }        
    }
}
