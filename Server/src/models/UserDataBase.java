package models;

import controller.UserThread;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDataBase {

    private ArrayList<UserInfo> users;
    private HashMap<String, Integer> usernameToId;
    private ArrayList<UserThread> threads;
    private ArrayList<Boolean> userState;

    public UserDataBase() {
        this.users = new ArrayList<>();
        this.usernameToId = new HashMap<>();
        this.threads = new ArrayList<>();
        this.userState = new ArrayList<>();
    }

    public synchronized int Size() {
        return users.size();
    }

    public synchronized UserInfo GetUser(int userId) {
        return users.get(userId);
    }

    public synchronized UserThread GetThread(int userId) {
        return threads.get(userId);
    }

    public synchronized void InsertUser(UserInfo newUser, UserThread thread) {
        newUser.SetUserId(users.size());
        threads.add(thread);
        users.add(newUser);
        usernameToId.put(newUser.GetUsername(), newUser.GetId());
        userState.add(true);
    }

    public synchronized void LoginUserState(int userId, UserThread thread) {
        threads.set(userId, thread);
        userState.set(userId, true);
    }

    public synchronized void LogoutUserState(int userId) {
        threads.set(userId, null);
        userState.set(userId, false);
    }

    public synchronized void SetUserState(int index, boolean newState) {
        userState.set(index, newState);
    }

    public synchronized Integer GetUserIdByName(String username) {
        return usernameToId.get(username);
    }

    public synchronized UserInfo GetUserByName(String username) {
        return GetUser(GetUserIdByName(username));
    }

    public synchronized boolean IsUserExist(String username) {
        return usernameToId.containsKey(username);
    }

    public synchronized boolean IsUserOnline(int userId) {
        return userState.get(userId);
    }
}
