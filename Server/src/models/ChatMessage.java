package models;

import java.io.Serializable;

public class ChatMessage implements Serializable {

    public static final int QUERY = 1;
    public static final int MESSAGE = 2; // chat message
    public static final int RESPONSE = 4;
    public static final int ERROR = 8;

    private String fromName;
    private String toName;
    private int type; // 1: Query 2: Message 3: Response 4: Error

    private String message;

    public ChatMessage(String fromName, String toName, int type, String message) {
        this.fromName = fromName;
        this.toName = toName;
        this.type = type;
        this.message = message;
    }

    public ChatMessage(ChatMessage that) {
        this.fromName = that.fromName;
        this.toName = that.toName;
        this.type = that.type;
        this.message = that.message;
    }

    public String GetFromName() {
        return fromName;
    }

    public String GetToName() {
        return toName;
    }

    public int GetType() {
        return type;
    }

    public String GetMessage() {
        return message;
    }

    public boolean isContainType(int types) {return (type & types) != 0;}
}
