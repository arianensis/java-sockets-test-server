import java.io.Serializable;

public class Message implements Serializable {
    final String contents;
    final int x;
    final int y;
    public Message(String contents) {
        this.contents = contents;
        // check if first eight characters are digits
        boolean firstEightAreDigits = true;
        for (int i=0; i<8; i++) if (!Character.isDigit(contents.charAt(i))) {
            firstEightAreDigits = false;
            break;
        }
        // if they are not 8 digits, set the x and y values to -1
        if (!firstEightAreDigits) {
            this.x = -1; this.y = -1;
        } else { // otherwise parse the first 4 for x and the next 4 for y
            this.x = Integer.parseInt(contents.substring(0,4));
            this.y = Integer.parseInt(contents.substring(4, 8));
        }
    }

    @Override
    public String toString(){return contents;}
    public int getX(){ return this.x; }
    public int getY(){ return this.y; }
}
