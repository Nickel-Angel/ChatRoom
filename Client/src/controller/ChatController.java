package controller;

import models.ChatMessage;
import models.MessageBuffer;
import models.UserInfo;
import view.ChatFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatController {

    private final ChatFrame mainFrame;

    private final MessageBuffer buffer;
    private final UserInfo mainUser;
    private final ArrayList<UserInfo> tabUsers;
    private final HashMap<String, Boolean> userExist;

    public ChatController(String host, int port) throws IOException {
        this.mainUser = new UserInfo(null);
        // model
        this.buffer = new MessageBuffer(host, 9090);
        this.tabUsers = new ArrayList<>();
        this.userExist = new HashMap<>();
        // view
        this.mainFrame = new ChatFrame(this);
        // begin
        TabHandler tabHandler = new TabHandler(this);
        tabHandler.Handle();
    }

    // view
    public ChatFrame GetMainFrame() {
        return mainFrame;
    }

    // username
    public String GetTabUsername(int index) {
        return tabUsers.get(index).GetUsername();
    }

    public void SetUsername(String username) {
        mainUser.SetUsername(username);
    }

    public String GetUsername() {
        return mainUser.GetUsername();
    }

    public int AddUser(String username) {
        userExist.put(username, true);
        tabUsers.add(new UserInfo(username));
        return tabUsers.size();
    }

    public boolean IsUserExist(String username) {
        return userExist.containsKey(username);
    }

    // models
    public ChatMessage Get(String fromName, int types) {
        ChatMessage result = null;
        while (result == null) {
            result = buffer.getFromBuffer(fromName, types);
            try {
                Thread.sleep((long)(Math.random() * 5));
            } catch (InterruptedException e) {
            }
        }
        return result;
    }

    public void Send(ChatMessage msg) {
        buffer.Send(msg);
    }

    // socket close
    public void Close() {
        buffer.Close();
    }
}
