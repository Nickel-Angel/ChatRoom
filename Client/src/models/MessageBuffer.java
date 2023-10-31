package models;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessageBuffer {

    private static final long EXPIRE_TIME = 5000;

    private final Socket socket;
    private final ObjectInputStream OIs;
    private final ObjectOutputStream OOs;

    private ChatMessage currentMessage;

    private long expireAt;

    public MessageBuffer(String host, int port) throws IOException {
        this.socket = new Socket(host, port);

        this.OOs = new ObjectOutputStream(socket.getOutputStream());
        this.OIs = new ObjectInputStream(socket.getInputStream());
        this.currentMessage = null;

        this.expireAt = System.currentTimeMillis();
    }

    public synchronized ChatMessage getFromBuffer(String fromName, int types) {
        if (System.currentTimeMillis() > expireAt) {
            currentMessage = null;
        }
        if (currentMessage != null) {
            ChatMessage msg = getCheckedMessage(currentMessage, fromName, types);
            if (msg == null)
                return null;
            currentMessage = null;
            return msg;
        }
        try {
            currentMessage = (ChatMessage) OIs.readObject();
            expireAt = System.currentTimeMillis() + EXPIRE_TIME;
        } catch (EOFException e) {
            currentMessage = new ChatMessage(null, null, ChatMessage.MESSAGE, "Logout Verified");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        }
        ChatMessage msg = getCheckedMessage(currentMessage, fromName, types);
        if (msg == null)
            return null;
        currentMessage = null;
        return msg;
    }

    private synchronized ChatMessage getCheckedMessage(ChatMessage currentMessage, String fromName, int types) {
        if (currentMessage.GetFromName() == null && fromName == null) {
            return new ChatMessage(currentMessage);
        }
        if (currentMessage.GetFromName() != null && currentMessage.GetFromName().equals(fromName)
                && currentMessage.isContainType(types)) {
            return new ChatMessage(currentMessage);
        }
        return null;
    }

    public void Send(ChatMessage msg) {
        synchronized (OOs) {
            try {
                OOs.writeObject(msg);
                OOs.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void Close() {
        try {
            OIs.close();
            OOs.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
