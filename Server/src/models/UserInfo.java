package models;

public class UserInfo {

    private String username;
    private String password;
    private int userId;

    public UserInfo(String username, String password, int userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public void SetUsername(String username) {
        this.username = username;
    }

    public String GetUsername() {
        return username;
    }

    public void SetPassword(String password) {
        this.password = password;
    }

    public String GetPassword() {
        return password;
    }

    public void SetUserId(int id) {
        this.userId = id;
    }

    public int GetId() {
        return userId;
    }
}

