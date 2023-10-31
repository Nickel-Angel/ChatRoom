package models;

public class UserInfo {

    private String username;

    public UserInfo(String username) {
        this.username = username;
    }

    public void SetUsername(String username) {
        this.username = username;
    }

    public String GetUsername() {
        return username;
    }
}
