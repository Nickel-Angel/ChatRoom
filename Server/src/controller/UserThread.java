package controller;

import models.ChatMessage;
import models.UserDataBase;
import models.UserInfo;

import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UserThread implements Runnable {

    private final ServerController controller;
    private final UserDataBase db;

    private Socket socket;
    private ObjectOutputStream OOs;
    private ObjectInputStream OIs;

    private UserInfo mainUser;

    public UserThread(ServerController controller, Socket socket) throws IOException {
        this.controller = controller;
        this.socket = socket;
        this.OIs = new ObjectInputStream(socket.getInputStream());
        this.OOs = new ObjectOutputStream(socket.getOutputStream());
        this.db = controller.getDb();
        this.mainUser = new UserInfo(null, null, -1);
    }

    public void Close() {
        db.LogoutUserState(mainUser.GetId());
        try {
            synchronized (db) {
                int currentSize = db.Size();
                for (int i = 0; i < currentSize; ++i) {
                    if (i == mainUser.GetId() || !db.IsUserOnline(i)) {
                        continue;
                    }
                    ObjectOutputStream otherOOs = db.GetThread(i).GetOOs();
                    otherOOs.writeObject(new ChatMessage(mainUser.GetUsername(), db.GetUser(i).GetUsername(),
                            ChatMessage.RESPONSE, "Logout"));
                    otherOOs.flush();
                }
            }
            System.out.println("Closed");
            controller.ClientCountMinus();
            if (OOs != null) {
                OOs.close();
            }
            if (OIs != null) {
                OIs.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectOutputStream GetOOs() {
        return OOs;
    }

    @Override
    public void run() {
        try {
            System.out.println("Start");
            ChatMessage msg;
            // login
            while (true) {
                msg = (ChatMessage) OIs.readObject();
                mainUser.SetUsername(msg.GetMessage());
                msg = (ChatMessage) OIs.readObject();
                mainUser.SetPassword(msg.GetMessage());
                if (db.IsUserExist(mainUser.GetUsername())) {
                    UserInfo temp = db.GetUserByName(mainUser.GetUsername());
                    if (temp.GetPassword().equals(mainUser.GetPassword())) {
                        mainUser = temp;
                        db.LoginUserState(mainUser.GetId(), this);
                        OOs.writeObject(new ChatMessage(null, mainUser.GetUsername(),
                                ChatMessage.RESPONSE, "Login success"));
                        OOs.flush();
                        break;
                    } else {
                        OOs.writeObject(new ChatMessage(null, mainUser.GetUsername(),
                                ChatMessage.RESPONSE, "Password wrong"));
                        OOs.flush();
                    }
                } else {
                    db.InsertUser(mainUser, this);
                    OOs.writeObject(new ChatMessage(null, mainUser.GetUsername(),
                            ChatMessage.RESPONSE, "Login success"));
                    OOs.flush();
                    break;
                }
            }

            synchronized (db) {
                int currentSize = db.Size();
                for (int i = 0; i < currentSize; ++i) {
                    if (i == mainUser.GetId() || !db.IsUserOnline(i)) {
                        continue;
                    }
                    OOs.writeObject(new ChatMessage(null, mainUser.GetUsername(),
                            ChatMessage.MESSAGE, db.GetUser(i).GetUsername()));
                    ObjectOutputStream otherOOs = db.GetThread(i).GetOOs();
                    otherOOs.writeObject(new ChatMessage(null, db.GetUser(i).GetUsername(),
                            ChatMessage.MESSAGE, mainUser.GetUsername()));
                    otherOOs.flush();
                }
            }

            while (true) {
                while ((msg = (ChatMessage) OIs.readObject()) == null);
                if (msg.GetToName() != null) {
                    synchronized (db) {
                        int index = db.GetUserIdByName(msg.GetToName());
                        ObjectOutputStream otherOOs = db.GetThread(index).GetOOs();
                        otherOOs.writeObject(new ChatMessage(mainUser.GetUsername(), msg.GetToName(),
                                ChatMessage.MESSAGE, msg.GetMessage()));
                        otherOOs.flush();
                    }
                } else {
                    if (msg.GetMessage().equals("Logout")) {
                        OOs.writeObject(new ChatMessage(null, mainUser.GetUsername(),
                                ChatMessage.MESSAGE, "Logout verified"));
                        OOs.flush();
                        Close();
                        break;
                    } else {
                        System.out.println(msg.GetMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Close();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Close();
        } catch (ClassNotFoundException e) {
        }
    }
}
