import controller.ChatController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ChatController mainController = new ChatController("localhost", 9090);
    }
}