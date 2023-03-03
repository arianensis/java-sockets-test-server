import java.io.Serializable;

public class Message implements Serializable {
    final String contents;
    public Message(String contents){this.contents = contents;}

    @Override
    public String toString(){return contents;}
}
