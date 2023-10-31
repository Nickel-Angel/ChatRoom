package controller;

import models.UserDataBase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {

    private UserDataBase db;
    private int ClientCount;

    public ServerController(int port) throws IOException {
        this.db = new UserDataBase();
        ClientCount = 0;
        ServerSocket server = new ServerSocket(port);
        while (true) {
            try {
                Socket socket = server.accept();
                new Thread(new UserThread(this, socket)).start();
                ++ClientCount;
            } catch (IOException e) {
            }
        }
    }

    public void ClientCountMinus() {
        --ClientCount;
    }

    public UserDataBase getDb() {
        return db;
    }
}
